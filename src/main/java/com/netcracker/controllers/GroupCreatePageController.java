package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.service.GroupService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/groups" )
public class GroupCreatePageController{

    @Autowired GroupService  groupService;
    @Autowired PersonService personService;

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

}
