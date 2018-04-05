package com.netcracker.controllers;

import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( value = "/persons" )
public class PersonSearchPageController{

    @Autowired
    PersonService personService;

    /**
     * Open user`s search page with pre-filled fields
     * @param name - user`s name
     * @param raceID - user`s race
     * @param ageFrom - minimum user`s age
     * @param ageTo - maximum user`s age
     * @param sex - user`s sex
     * @param model - model to store params
     * @return search page
     */
    @GetMapping( value = "/search" )
    public String searchParams(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "raceId", defaultValue = "" )
                    Long raceID ,
            @RequestParam( value = "ageFrom", defaultValue = "" )
                    Integer ageFrom ,
            @RequestParam( value = "ageTo", defaultValue = "" )
                    Integer ageTo ,
            @RequestParam( value = "sex", defaultValue = "" )
                    String sex , Model model ){

        model.addAttribute( "name" , name );
        model.addAttribute( "raceId" , raceID );
        model.addAttribute( "ageFrom" , ageFrom );
        model.addAttribute( "ageTo" , ageTo );
        model.addAttribute( "sex" , sex );
        return "personSearchPage";
    }

    /**
     * Open user`s search result page
     * @param name - user`s name
     * @param raceID - user`s race
     * @param ageFrom - minimum user`s age
     * @param ageTo - maximum user`s age
     * @param sex - user`s sex
     * @param model - model to store params
     * @return search result page
     */
    @GetMapping( value = "/search/result" )
    public String searchResult(
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "raceId", defaultValue = "" )
                    Long raceID ,
            @RequestParam( value = "ageFrom", defaultValue = "" )
                    Integer ageFrom ,
            @RequestParam( value = "ageTo", defaultValue = "" )
                    Integer ageTo ,
            @RequestParam( value = "sex", defaultValue = "" )
                    String sex , Model model ){
        model.addAttribute( "persons" ,
                personService.getBySearchParams( name ,
                        raceID ,
                        ageFrom ,
                        ageTo ,
                        sex ) );

        return "personSearchResult";
    }
}
