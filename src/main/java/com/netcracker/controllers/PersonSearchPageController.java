package com.netcracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( value = "/persons" )
public class PersonSearchPageController{

    @RequestMapping( value = "/search", method = RequestMethod.GET )
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
}
