package com.netcracker.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.DAO.*;
import com.netcracker.controllers.forms.GroupCreateForm;
import com.netcracker.controllers.forms.GroupCreateValidator;
import com.netcracker.controllers.forms.LoginForm;
import com.netcracker.controllers.forms.RegistrationForm;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.MessageRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 Controller for groups. Group page, group create, group settings
 */
@Controller
@RequestMapping( "/groups" )
public class GroupPageController{

    @Autowired PersonService     personService;
    @Autowired PersonRepository  personRepository;
    @Autowired MessageService    messageService;
    @Autowired MessageRepository messageRepository;
    @Autowired GroupService      groupService;
    @Autowired GroupRepository   groupRepository;
    @Autowired ObjectMapper      mapper;
    @Autowired GroupCreateValidator groupCreateValidator;

    @InitBinder( "groupCreateForm" )
    protected void initGroupCreateBinder( WebDataBinder binder ){
        binder.setValidator( groupCreateValidator );
    }

    @ModelAttribute( "groupCreateForm" )
    public GroupCreateForm groupCreateForm(){
        return new GroupCreateForm();
    }

    /**
     Open group page

     @param groupId - group ID, path
     @param model   - model to store params

     @return - group page
     */
    @GetMapping("/{id}")
    public String openPage(
            @PathVariable( value = "id" )
                    Long groupId ,
            @CookieValue( name = "userID", required = false )
                    Long userId , Model model ){
        if( userId == null ) return "redirect:/";
//        todo Обработка ошибок
        GroupEntity groupEntity =
                groupService.findById( groupId , GroupLazyFields.MESSAGES , GroupLazyFields.USERS )
                            .get();
        model.addAttribute( "formatter" , DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) );
        model.addAttribute( "group" , groupEntity );
        model.addAttribute( "isMember" ,
                            groupEntity.getUsers()
                                       .parallelStream()
                                       .map( PersonEntity::getId )
                                       .anyMatch( Predicate.isEqual( userId ) ) );

        return "groupPage";
    }

    /**
     Ajax request to get all messages of group

     @return json array contains messages
     */
    @GetMapping( value = "/{id}/messages", produces = "application/json; charset=UTF-8" )
    public @ResponseBody
    String getAllMessagesOfGroup(
            @PathVariable( "id" )
                    Long groupId ,
            @CookieValue( name = "userID", required = false )
                    Long writerId ) throws IOException{
        if( writerId == null ){
            return mapper.writeValueAsString( new LinkedHashMap<String, String>(){{
                put( "error" , "home" );
            }} );
        }
        return mapper.writeValueAsString( groupRepository.getMessagesById( groupId )
                                                         .parallelStream()
                                                         .sorted( Comparator.comparing(
                                                                 MessageEntity::getDateOfSubmition ) )
                                                         .map( this::messageToJsonMap )
                                                         .collect( Collectors.toList() ) );
    }

    @PostMapping( value = "/{id}/message" )
    public @ResponseBody
    String postMessage(
            @CookieValue( name = "userID", required = false )
                    Long writerId ,
            @PathVariable( "id" )
                    Long groupId ,
            @RequestBody
                    Map<String, String> requestBody , Model model ){
        if( writerId == null || requestBody.get( "messageText" ).isEmpty()) return "home";
        try{
            messageService.addMessage( groupId , writerId , requestBody.get( "messageText" ) );
            return "success";
        }catch( IllegalArgumentException e ){
            return "fail";
        }
    }

    /**
     Ajax request to delete message from group
     */
    @DeleteMapping( value = "/{id}/message-{messageId}" )
    public @ResponseBody
    String deleteMessage(
            @CookieValue( value = "userID", required = false )
                    Long writerId ,
            @PathVariable( "messageId" )
                    Long messageId ){
        if( writerId == null ) return "home";
        return messageRepository.findById( messageId ).map( message -> {
            if( !message.getWriter().getId().equals( writerId ) && !message.getToGroup().getOwner().getId().equals( writerId )) return "fail";
            messageRepository.deleteById( messageId );
            return "success";
        } ).orElse( "fail" );
    }

    private LinkedHashMap<String, String> messageToJsonMap( MessageEntity message ){
        return new LinkedHashMap<String, String>(){{
            put( "id" , message.getId().toString() );
            put( "text" , message.getText() );
            put( "date" ,
                 message.getDateOfSubmition()
                        .format( DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) ) );
            put( "writerId" , message.getWriter().getId().toString() );
            put( "writerName" , message.getWriter().getName() );
            put( "writerDeleted" , message.getWriter().getDeleted().toString() );
        }};
    }

    /**
     Response to ajax request to join the group

     @param userId  - cookied user ID, who wants to join
     @param groupId - group ID, path, where to join

     @return - json object of user's data
     */
    @PostMapping( value = "/{id}/join", produces = "application/json; charset=UTF-8" )
    public @ResponseBody
    String joinGroup(
            @CookieValue( name = "userID", required = false )
                    Long userId ,
            @PathVariable( value = "id" )
                    Long groupId ) throws JsonProcessingException{
        if( userId == null ){
            return mapper.writeValueAsString( new LinkedHashMap<String, String>(){{
                put( "error" , "home" );
            }} );
        }

//        todo Обработка ошибок
        GroupEntity  group  = groupService.findById( groupId , GroupLazyFields.USERS ).get();
        PersonEntity person = personRepository.findById( userId ).get();
        if( group.getUsers()
                 .parallelStream()
                 .map( PersonEntity::getId )
                 .anyMatch( Predicate.isEqual( userId ) ) ) return "fail";
        personService.joinGroup( groupId , userId );
        return mapper.writeValueAsString( new LinkedHashMap<String, String>(){{
            put( "id" , person.getId().toString() );
            put( "name" , person.getName() );
            put( "race" , person.getRace().getName() );
            put( "age" , person.getAge().toString() );
        }} );
    }

    /**
     Leaving the group

     @param userId  - cookied user ID, who wants to leave
     @param groupId - group ID, path, from where leave
     @param model   - model to store params

     @return - group page
     */
    @PostMapping( value = "/{id}/leave" )
    public @ResponseBody
    String leaveGroup(
            @CookieValue( name = "userID", required = false )
                    Long userId ,
            @PathVariable( value = "id" )
                    Long groupId , Model model ){
        if( userId == null ) return "home";
        GroupEntity group = groupService.findById( groupId , GroupLazyFields.USERS ).get();
        if( group.getUsers()
                 .parallelStream()
                 .map( PersonEntity::getId )
                 .noneMatch( Predicate.isEqual( userId ) ) ) return "fail";
        personService.leaveGroup( groupId , userId );
        return userId.toString();
    }

    @GetMapping( value = "/create" )
    public String openGroupCreatePage(
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId ){

        if( userId == null ){
            return "redirect:/";
        }

        return "redirect:/persons/"+userId;
    }
//
    /**
     Creating page

     @param model   - model to store params

     @return - group page
     */
    @PostMapping( value = "/create" )
    public String createGroup(
            @Validated
            @ModelAttribute( "groupCreateForm" )
                    GroupCreateForm groupCreateForm , BindingResult errors , Model model ,
            HttpServletResponse response ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId ) throws IOException{

//        todo : Валидация данных

        if( userId == null ){
            return "redirect:/";
        }

        if( errors.hasErrors() ){
            return "personPage";
        }

        Long groupId = groupService.addGroup(groupCreateForm,userId);
        personService.joinGroup(groupId,userId);

        return "redirect:/groups/"+groupId;
    }
//
//    //Group settings
//
//    /**
//     open settings page
//
//     @param id    - group ID, path
//     @param model - model to store params
//
//     @return - group settings page
//     */
//    @GetMapping( value = "/{id}/settings" )
//    public String openSettings(
//            @PathVariable( value = "id" )
//                    Long id ,
//            @CookieValue( name = "userID" )
//                    Long userId , Model model ){
//        if( ( userId == null ) || ( groupService.getById( id ).getOwner().getId() != userId ) ){
//            return "redirect:/";
//        }
//        model.addAttribute( "group" , groupService.getById( id ) );
//        return "groupSettingsPage";
//    }
//
//    /**
//     update settings of group
//
//     @param newName - new name of group
//     @param id      - group ID, path
//     @param model   - model to store params
//
//     @return - group settings page
//     */
//    @PostMapping( value = "/{id}/settings", params = "newName" )
//    public String updateGroup(
//            @RequestParam( value = "newName", defaultValue = "" )
//                    String newName ,
//            @PathVariable( value = "id" )
//                    Long id ,
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long userId , Model model ){
//
//        if( ( userId == null ) || ( groupService.getById( id ).getOwner().getId() != userId ) ){
//            return "redirect:/";
//        }
//
//        GroupEntity toEdit = groupService.getById( id );
//        toEdit.setName( newName );
//        model.addAttribute( "group" , groupService.editGroup( toEdit ) );
//        return "groupSettingsPage";
//    }

}
