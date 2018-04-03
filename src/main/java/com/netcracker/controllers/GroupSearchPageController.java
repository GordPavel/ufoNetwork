package com.netcracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( "/groups" )
public class GroupSearchPageController{

    @GetMapping( value = "/search" )
    public String searchParams(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "ownerName", defaultValue = "" )
                    String ownerName , Model model ){

        model.addAttribute( "name" , name );
        model.addAttribute( "ownerName" , ownerName );

        return "groupSearchPage";
    }
}
