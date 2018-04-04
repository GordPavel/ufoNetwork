package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.MessageEntity;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/groups" )
public class GroupPageController{

    @Autowired PersonService personService;

    @Autowired MessageService messageService;

    @Autowired GroupService groupService;

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String openPage(
            @PathVariable( value = "id" )
                    Long id , Model model ){

        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    //    todo : Если я правильно понимаю, здесь должен быть post
    @PostMapping( value = "/{id}", params = "message" )
    public String postMessage(
            @CookieValue( value = "userID", defaultValue = "" )
                    Long writer ,
            @RequestParam( value = "message" )
                    String messageText ,
            @PathVariable( value = "id" )
                    Long id , Model model ){
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setToGroup( groupService.getById( id ) );
        messageEntity.setText( messageText );
        messageEntity.setWriter( personService.getById( writer ) );

        messageService.addMessage( messageEntity );

        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    //    todo : Если я правильно понимаю, здесь должен быть delete
    @DeleteMapping( value = "/{id}", params = "messageId" )
    public String deleteMessage(
            @RequestParam( value = "messageId", defaultValue = "" )
                    Long messageId ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        messageService.delete( messageId );
        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    //    todo : Если я правильно понимаю, здесь должен быть post
    @PostMapping( value = "/{id}/join" )
    public String joinGroup(
            @CookieValue( value = "userID", defaultValue = "" )
                    Long join ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        personService.joinGroup( id , join );
        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    //    todo : Если я правильно понимаю, здесь должен быть post
    @PostMapping( value = "/{id}/leave" )
    public String leaveGroup(
            @CookieValue( value = "userID", defaultValue = "" )
                    Long leaveId ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        personService.leaveGroup( id , leaveId );
        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    //Group creation

    @GetMapping( value = "/create" )
    public String openGroupCreatePage(
            @RequestParam( value = "name", defaultValue = "" )
                    String name , Model model ){

        model.addAttribute( "name" , name );

        return "groupCreatePage";
    }

    @PostMapping( value = "/create" )
    public String createGroup(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @CookieValue( value = "userID", defaultValue = "" )
                    Long ownerID , Model model ){

//        todo : Валидация данных

        GroupEntity groupEntity = new GroupEntity( name , personService.getById( ownerID ) );
        groupService.addGroup( groupEntity );
        model.addAttribute( "group" , groupEntity );

        return "groupPage";
    }

    //Group settings

    @RequestMapping( value = "/{id}/settings", method = RequestMethod.GET )
    public String openSettings(
            @PathVariable( value = "id" )
                    Long id , Model model ){

        model.addAttribute( "group" , groupService.getById( id ) );
        return "groupSettingsPage";
    }

    @RequestMapping( value = "/{id}/settings", params = "newName" )
    public String updateGroup(
            @RequestParam( value = "newName", defaultValue = "" )
                    String newName ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        GroupEntity toEdit = groupService.getById( id );
        toEdit.setName( newName );
        model.addAttribute( "group" , groupService.editGroup( toEdit ) );
        return "groupSettingsPage";
    }

}
