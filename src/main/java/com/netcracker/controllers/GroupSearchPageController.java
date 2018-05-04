package com.netcracker.controllers;

import com.netcracker.DAO.RaceEntity;
import com.netcracker.controllers.forms.*;
import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class GroupSearchPageController{

    @Autowired
    GroupService groupService;

    @Autowired SearchGroupsFormValidator searchGroupsFormValidator;

    @InitBinder("searchGroupsForm")
    protected void initSearchGroupBinder( WebDataBinder binder ){ binder.setValidator( searchGroupsFormValidator ); }

    @ModelAttribute("searchGroupsForm")
    public SearchGroupsForm searchGroupsForm () { return new SearchGroupsForm();}


    /*/**
      * open search page, contain pre-filled with params fields
      * @param name - group name for pre-filling field
      * @param ownerName - owner name for pre-filling field
      * @param model - model to contain params
      * @return - search page
      */
   /* @GetMapping( value = "/search" )
    public String searchPage(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "ownerName", defaultValue = "" )
                    String ownerName ,
            @CookieValue( name = "userID", defaultValue = "")
                    Long userId,
            Model model ){

        if (userId == null){
            return "redirect:/";
        }
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
    /*@GetMapping( value = "/search/result" )
    public String searchResult(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "ownerName", defaultValue = "" )
                    String ownerName ,
            @CookieValue( name = "userID", defaultValue = "")
                    Long userId,
            Model model ){

        if (userId == null){
            return "redirect:/";
        }
        model.addAttribute( "groups" , groupService.getBySearchParams( name , ownerName ) );

        return "searchGroupPage";
    }
*/
    @PostMapping( value = "/srcgroups" )
    public String searchPersons (  @Validated
                                   @ModelAttribute( "searchGroupsForm" ) SearchGroupsForm searchGroupsForm ,
                                   Model model ,
                                   HttpServletResponse response ,
                                   BindingResult bindingResult){
        if( bindingResult.hasErrors() ) return "redirect:/";

        model.addAttribute( "groups" , groupService.getBySearchParams( searchGroupsForm.getName(),
                                                                          searchGroupsForm.getOwnerName() ) );

        return "searchGroupPage";
    }

     @GetMapping( value = "/searchgroups" )
    public String searchPage(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "ownerName", defaultValue = "" )
                    String ownerName ,
            @CookieValue( name = "userID", defaultValue = "")
                    Long userId,
            Model model ){

        if (userId == null){
            return "redirect:/";
        }
        model.addAttribute( "name" , name );
        model.addAttribute( "ownerName" , ownerName );

        return "groupSearchPage";
    }





}
