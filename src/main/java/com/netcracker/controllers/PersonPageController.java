package com.netcracker.controllers;

import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( value = "/persons" )
public class PersonPageController{

    @Autowired PersonService personService;

    @GetMapping( value = "/page/{id}" )
    public String openPage(
            @PathVariable( value = "id" )
                    Long id , Model model ){
        model.addAttribute( "person" , personService.getById( id ) );
        return "personPage";
    }
}
