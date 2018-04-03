package com.netcracker.controllers;

import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( value = "/persons/search" )
public class PersonSearchResultController{

    @Autowired PersonService personService;

    @RequestMapping( value = "/result", method = RequestMethod.GET )
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
