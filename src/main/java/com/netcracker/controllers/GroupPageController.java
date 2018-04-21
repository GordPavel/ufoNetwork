package com.netcracker.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.GroupLazyFields;
import com.netcracker.DAO.MessageEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.MessageRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
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

    /**
     Open group page

     @param groupId - group ID, path
     @param model   - model to store params

     @return - group page
     */
    @GetMapping( value = "/{id}" )
    public String openPage(
            @PathVariable( value = "id" )
                    Long groupId ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId , Model model , HttpServletRequest request ){
        if( userId == null ){
            return "redirect:/";
        }
//        todo Обработка ошибок
        GroupEntity groupEntity =
                groupService.findById( groupId , GroupLazyFields.MESSAGES , GroupLazyFields.USERS )
                            .get();
        //noinspection ResultOfMethodCallIgnored
        ZoneId zoneId =
                ZoneId.of( Calendar.getInstance( request.getLocale() ).getTimeZone().getID() );
        groupEntity.getMessages()
                   .parallelStream()
                   .peek( message -> message.setDateOfSubmition( message.getDateOfSubmition()
                                                                        .withZoneSameInstant( zoneId ) ) )
                   .close();
        model.addAttribute( "formatter" , DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) );
        model.addAttribute( "group" , groupEntity );

        return "groupPage";
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
        }};
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
    @DeleteMapping( value = "/message-{id}" )
    public @ResponseBody
    String deleteMessage(
            @CookieValue( value = "userID", required = false )
                    Long writerId ,
            @PathVariable( "id" )
                    Long messageId ){
        return messageRepository.findById( messageId ).map( message -> {
            if( !message.getWriter().getId().equals( writerId ) ) return null;
            messageRepository.deleteById( messageId );
            return "success";
        } ).orElse( "fail" );
    }

    @GetMapping( value = "/{id}/messages", produces = "application/json; charset=UTF-8" )
    public @ResponseBody
    String getAllMessagesOfGroup(
            @PathVariable( "id" )
                    Long groupId ) throws JsonProcessingException{
        String response = mapper.writeValueAsString( groupRepository.getMessagesById( groupId )
                                                             .parallelStream()
                                                             .sorted( Comparator.comparing(
                                                                     MessageEntity::getDateOfSubmition ) )
                                                             .map( this::messageToJsonMap )
                                                             .collect( Collectors.toList() ) );
        return response;
    }

    /**
     Joining the group

     @param join  - cookied user ID, who wants to join
     @param id    - group ID, path, where to join
     @param model - model to store params

     @return - group page
     */
    @GetMapping( value = "/{id}/join" )
    public String joinGroup(
            @CookieValue( name = "userID", defaultValue = "" )
                    Long join ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        if( join == null ){
            return "redirect:/";
        }

//        todo Ленивая Загрузка
        if( !personRepository.getGroupsById( id )
                             .contains( groupRepository.findById( id ).get() ) ){
            personService.joinGroup( id , join );
        }else{
            //TODO: error message implementation
            model.addAttribute( "error_message" , "user already in group" );
        }
        model.addAttribute( "group" , groupRepository.findById( id ) );

        return "redirect:/groups/" + id;
    }

    /**
     Leaving the group

     @param leave - cookied user ID, who wants to leave
     @param id    - group ID, path, from where leave
     @param model - model to store params

     @return - group page
     */
    @GetMapping( value = "/{id}/leave" )
    public String leaveGroup(
            @CookieValue( name = "userID", defaultValue = "" )
                    Long leave ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        if( leave == null ){
            return "redirect:/";
        }

//        todo Ленивая загрузка
        if( personRepository.findById( leave )
                            .get()
                            .getGroups()
                            .contains( groupRepository.findById( id ).get() ) ){
            personService.leaveGroup( id , leave );
        }else{
            //TODO: error mesage implementation
            model.addAttribute( "error_message" , "user not in group" );
        }
        model.addAttribute( "group" , groupRepository.findById( id ) );

        return "redirect:/groups/" + id;
    }
//
//    //Group creation
//
//    /**
//     open group crate page
//
//     @param name  - name of group, param to pre-fill name page
//     @param model - model to store params
//
//     @return - group creation page
//     */
//    @GetMapping( value = "/create" )
//    public String openGroupCreatePage(
//            @RequestParam( value = "name", defaultValue = "" )
//                    String name ,
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long userId , Model model ){
//
//        if( userId == null ){
//            return "redirect:/";
//        }
//
//        model.addAttribute( "name" , name );
//
//        return "groupCreatePage";
//    }
//
//    /**
//     Creating page
//
//     @param name    - name of group
//     @param model   - model to store params
//     @param ownerID - cookied user ID, owner of new group
//
//     @return - group page
//     */
//    @PostMapping( value = "/create" )
//    public String createGroup(
//            @RequestParam( value = "name", defaultValue = "" )
//                    String name ,
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long ownerID , Model model ){
//
////        todo : Валидация данных
//
//        if( ownerID == null ){
//            return "redirect:/";
//        }
//
//        Pattern p = Pattern.compile( "[\\d\\s-_]+" );
//        Matcher m = p.matcher( name );
//        if( m.matches() ){
//            GroupEntity groupEntity = new GroupEntity( name , personService.getById( ownerID ) );
//            groupService.addGroup( groupEntity );
//            model.addAttribute( "group" , groupEntity );
//        }else{
//            //TODO: error mesage implementation
//            model.addAttribute( "error_message" , "unacceptable name" );
//            return "groupCreatePage";
//        }
//
//        return "groupPage";
//    }
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
