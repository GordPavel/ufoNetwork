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


/**
 * Controller for groups. Group page, group create, group settings
 */
@Controller
@RequestMapping( "/groups" )
public class GroupPageController{

    @Autowired PersonService personService;

    @Autowired MessageService messageService;

    @Autowired GroupService groupService;

    /**
     * Open group page
     * @param id - group ID, path
     * @param model - model to store params
     * @return - group page
     */
    @GetMapping( value = "/{id}")
    public String openPage(
            @PathVariable( value = "id" )
                    Long id , Model model ){

        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    /**
     * Posting message in group
     * @param writer - cookied user ID who post this message
     * @param messageText - message text
     * @param id - group ID, path
     * @param model - model to store params
     * @return - group page
     */
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

    /**
     * Delete message from group
     * @param messageId - message to delete
     * @param id - group ID, path
     * @param model - model to store params
     * @return - group page
     */
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

    /**
     * Joining the group
     * @param join - cookied user ID, who wants to join
     * @param id - group ID, path, where to join
     * @param model - model to store params
     * @return - group page
     */
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

    /**
     * Leaving the group
     * @param leave - cookied user ID, who wants to leave
     * @param id - group ID, path, from where leave
     * @param model - model to store params
     * @return - group page
     */
    @PostMapping( value = "/{id}/leave" )
    public String leaveGroup(
            @CookieValue( value = "userID", defaultValue = "" )
                    Long leave ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        personService.leaveGroup( id , leave );
        model.addAttribute( "group" , groupService.getById( id ) );

        return "groupPage";
    }

    //Group creation

    /**
     * open group crate page
     * @param name - name of group, param to pre-fill name page
     * @param model - model to store params
     * @return - group creation page
     */
    @GetMapping( value = "/create" )
    public String openGroupCreatePage(
            @RequestParam( value = "name", defaultValue = "" )
                    String name , Model model ){

        model.addAttribute( "name" , name );

        return "groupCreatePage";
    }

    /**
     * Creating page
     * @param name - name of group
     * @param model - model to store params
     * @param ownerID - cookied user ID, owner of new group
     * @return - group page
     */
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

    /**
     * open settings page
     * @param id - group ID, path
     * @param model - model to store params
     * @return - group settings page
     */
    @GetMapping( value = "/{id}/settings")
    public String openSettings(
            @PathVariable( value = "id" )
                    Long id , Model model ){
        //TODO проверка, имеет ли пользователь право вообще сюда зайти
        model.addAttribute( "group" , groupService.getById( id ) );
        return "groupSettingsPage";
    }

    /**
     * update settings of group
     * @param newName - new name of group
     * @param id - group ID, path
     * @param model - model to store params
     * @return - group settings page
     */
    @PostMapping( value = "/{id}/settings", params = "newName" )
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
