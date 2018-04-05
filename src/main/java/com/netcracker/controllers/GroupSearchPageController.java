package com.netcracker.controllers;

import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( "/groups" )
public class GroupSearchPageController{

    @Autowired
    GroupService groupService;

    /**
     * open search page, contain pre-filled with params fields
     * @param name - group name for pre-filling field
     * @param ownerName - owner name for pre-filling field
     * @param model - model to contain params
     * @return - search page
     */
    @GetMapping( value = "/search" )
    public String searchPage(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "ownerName", defaultValue = "" )
                    String ownerName , Model model ){

        model.addAttribute( "name" , name );
        model.addAttribute( "ownerName" , ownerName );

        return "groupSearchPage";
    }

    /**
     * open search result page
     * @param name - group name to search
     * @param ownerName - owner name  to search
     * @param model - model to contain params
     * @return - search result page
     */
    @GetMapping( value = "/search/result" )
    public String searchResult(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "ownerName", defaultValue = "" )
                    String ownerName , Model model ){

        model.addAttribute( "groups" , groupService.getBySearchParams( name , ownerName ) );

        return "groupSearchResultPage";
    }
}
