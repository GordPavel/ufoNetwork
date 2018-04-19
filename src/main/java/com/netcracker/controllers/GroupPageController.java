package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.MessageEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.MessageRepository;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 Controller for groups. Group page, group create, group settings
 */
@Controller
@RequestMapping( "/groups" )
public class GroupPageController{

    @Autowired PersonService personService;

    @Autowired MessageService    messageService;
    @Autowired MessageRepository messageRepository;

    @Autowired GroupService groupService;

    /**
     Open group page

     @param id    - group ID, path
     @param model - model to store params

     @return - group page
     */
    @GetMapping( value = "/{id}" )
    public String openPage(
            @PathVariable( value = "id" )
                    Long id ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId , Model model ){

        if( userId == null ){
            return "redirect:/";
        }
        GroupEntity groupEntity = groupService.getById( id ).get();
        groupEntity.setMessages(messageService.getByGroup(id));
        model.addAttribute( "group" , groupEntity );

        return "groupPage";
    }

    /**
     Posting message in group

     @param writer      - cookied user ID who post this message
     @param messageText - message text
     @param id          - group ID, path
     @param model       - model to store params

     @return - group page
     */
    @PostMapping( value = "/{id}", params = "message" )
    public String postMessage(
            @CookieValue( name = "userID", defaultValue = "" )
                    Long writer ,
            @RequestParam( value = "message" )
                    String messageText ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        if( writer == null ){
            return "redirect:/";
        }


        MessageEntity messageEntity = new MessageEntity();
        //TODO: check present
        PersonEntity writerEntity = personService.getById( writer ).get();
        messageEntity.setToGroup( groupService.getById( id ).get() );
        /////////////////////////////////////
        messageEntity.setText( messageText.replaceAll("\n","</br>") );
        messageEntity.setWriter( writerEntity );
        messageEntity.setDateOfSubmition(new Date(System.currentTimeMillis()));

        if( groupService.getById( id ).get().getUsers().contains( writerEntity ) ){
            messageService.addMessage( messageEntity );
        }else{
            //TODO: error mesage implementation
            model.addAttribute( "error_message" , "user can`t write here" );
        }

        GroupEntity groupEntity = groupService.getById( id ).get();
        groupEntity.setMessages(messageService.getByGroup(id));
        model.addAttribute( "group" , groupEntity );

        return "redirect:/groups/"+id;
    }

    /**
     Delete message from group

     @param messageId - message to delete
     @param id        - group ID, path
     @param model     - model to store params

     @return - group page
     */
    @PostMapping( value = "/{id}", params = "messageId" )
    public String deleteMessage(
            @RequestParam( value = "messageId", defaultValue = "" )
                    Long messageId ,
            @PathVariable( value = "id" )
                    Long id ,
            @CookieValue( name = "userID" )
                    Long userId , Model model ){

        if( userId == null ){
            return "redirect:/";
        }

        //TODO: check present
        MessageEntity message = messageRepository.findById( messageId ).get();
        //////////////////////////
        if( !( message.getWriter().getId().equals( userId ) ||
            message.getToGroup().getOwner().getId().equals( userId ) ) ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "user can`t delete this" );
            return "redirect:/groups/"+id;
        }

        messageService.delete( messageId );
        GroupEntity groupEntity = groupService.getById( id ).get();
        groupEntity.setMessages(messageService.getByGroup(id));
        model.addAttribute( "group" , groupEntity );

        return "redirect:/groups/"+id;
    }
//
//    /**
//     Joining the group
//
//     @param join  - cookied user ID, who wants to join
//     @param id    - group ID, path, where to join
//     @param model - model to store params
//
//     @return - group page
//     */
//    @PostMapping( value = "/{id}/join" )
//    public String joinGroup(
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long join ,
//            @PathVariable( value = "id" )
//                    Long id , Model model ){
//
//        if( join == null ){
//            return "redirect:/";
//        }
//
//        if( !personService.getById( join ).getGroups().contains( groupService.getById( id ) ) ){
//            personService.joinGroup( id , join );
//        }else{
//            //TODO: error mesage implementation
//            model.addAttribute( "error_message" , "user already in group" );
//        }
//        model.addAttribute( "group" , groupService.getById( id ) );
//
//        return "groupPage";
//    }
//
//    /**
//     Leaving the group
//
//     @param leave - cookied user ID, who wants to leave
//     @param id    - group ID, path, from where leave
//     @param model - model to store params
//
//     @return - group page
//     */
//    @PostMapping( value = "/{id}/leave" )
//    public String leaveGroup(
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long leave ,
//            @PathVariable( value = "id" )
//                    Long id , Model model ){
//
//        if( leave == null ){
//            return "redirect:/";
//        }
//
//        if( personService.getById( leave ).getGroups().contains( groupService.getById( id ) ) ){
//            personService.leaveGroup( id , leave );
//        }else{
//            //TODO: error mesage implementation
//            model.addAttribute( "error_message" , "user not in group" );
//        }
//        model.addAttribute( "group" , groupService.getById( id ) );
//
//        return "groupPage";
//    }
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
