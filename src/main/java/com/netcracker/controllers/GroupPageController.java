package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.GroupLazyFields;
import com.netcracker.DAO.PersonEntity;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;


/**
 Controller for groups. Group page, group create, group settings
 */
@Controller
@RequestMapping( "/groups/{id}" )
public class GroupPageController{

    @Autowired PersonService     personService;
    @Autowired PersonRepository  personRepository;
    @Autowired MessageService    messageService;
    @Autowired MessageRepository messageRepository;
    @Autowired GroupService      groupService;
    @Autowired GroupRepository   groupRepository;

    /**
     Open group page

     @param groupId - group ID, path
     @param model   - model to store params

     @return - group page
     */
    @GetMapping
    public String openPage(
            @PathVariable( value = "id" )
                    Long groupId ,
            @CookieValue( name = "userID", required = false )
                    Long userId , Model model ){
        if( userId == null ) return "redirect:/";
//        todo Перевести на дефолтную страницу с сообщением об ошибке
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

    @GetMapping( value = "/messages/sse" )
    SseEmitter subscribeOnMessagesOfGroup(
            @PathVariable( "id" )
                    Long groupId ){
        return messageService.getMessagesEmbitterByGroupId( groupId );
    }

    @PostMapping( value = "/message",
                  consumes = "text/html;charset=utf-8",
                  produces = "text/html;charset=utf-8" )
    public @ResponseBody
    String postMessage(
            @CookieValue( name = "userID", required = false )
                    Long writerId ,
            @PathVariable( "id" )
                    Long groupId ,
            @RequestBody
                    String text , Model model ){
        if( writerId == null ) return "home";
        try{
            messageService.addMessage( groupId , writerId , text );
            return "success";
        }catch( IllegalArgumentException e ){
            return e.getMessage();
        }
    }

    /**
     Ajax request to delete message from group
     */
    @DeleteMapping( value = "/message-{messageId}", produces = "text/html;charset=utf-8" )
    public @ResponseBody
    String deleteMessage(
            @CookieValue( value = "userID", required = false )
                    Long writerId ,
            @PathVariable( "messageId" )
                    Long messageId ){
        if( writerId == null ) return "home";
        try{
            messageService.deleteMessage( writerId , messageId );
            return "success";
        }catch( IllegalArgumentException | IllegalStateException e ){
            return e.getMessage();
        }
    }

    /**
     Response to ajax request to join the group

     @param userId  - cookied user ID, who wants to join
     @param groupId - group ID, path, where to join

     @return - json object of user's data
     */
    @PostMapping( value = "/join", produces = "text/html;charset=utf-8" )
    public @ResponseBody
    String joinGroup(
            @CookieValue( name = "userID", required = false )
                    Long userId ,
            @PathVariable( value = "id" )
                    Long groupId ){
        if( userId == null ) return "home";
        try{
            personService.joinGroup( groupId , userId );
            return "success";
        }catch( IllegalStateException | IllegalArgumentException e ){
            return e.getMessage();
        }
    }

    /**
     Leaving the group

     @param userId  - cookied user ID, who wants to leave
     @param groupId - group ID, path, from where leave
     @param model   - model to store params

     @return - group page
     */
    @PostMapping( value = "/leave", produces = "text/html;charset=utf-8" )
    public @ResponseBody
    String leaveGroup(
            @CookieValue( name = "userID", required = false )
                    Long userId ,
            @PathVariable( value = "id" )
                    Long groupId , Model model ){
        if( userId == null ) return "home";
        try{
            personService.leaveGroup( groupId , userId );
            return "success";
        }catch( IllegalStateException | IllegalArgumentException e ){
            return e.getMessage();
        }
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
