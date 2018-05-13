package com.netcracker.controllers;

import com.netcracker.DAO.RaceEntity;
import com.netcracker.controllers.forms.*;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.Collectors;

@Controller
@RequestMapping( value = "/registration" )
public class RegistrationPageController {


    @Autowired RaceService raceService;
    @Autowired PersonService personService;

    @Autowired
    SearchPersonsFormValidator searchPersonsFormValidator;
    @Autowired
    SearchGroupsFormValidator searchGroupsFormValidator;
    @Autowired
    RegistrationValidator registrationValidator;

    @InitBinder("searchGroupsForm")
    protected void initSearchGroupBinder( WebDataBinder binder ){ binder.setValidator( searchGroupsFormValidator ); }

    @ModelAttribute("searchGroupsForm")
    public SearchGroupsForm searchGroupsForm () { return new SearchGroupsForm();}

    @InitBinder("searchPersonsForm")
    protected void initSearchPersonBinder( WebDataBinder binder ){ binder.setValidator( searchPersonsFormValidator ); }

    @ModelAttribute("searchPersonsForm")
    public SearchPersonsForm searchPersonsForm () { return new SearchPersonsForm();}

    @InitBinder( "registrationForm" )
    protected void initRegistrationBinder( WebDataBinder binder ){
        binder.setValidator( registrationValidator );
    }

    @ModelAttribute( "registrationForm" )
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    @GetMapping
    public String open( Model model ){
        model.addAttribute( "races" ,
                raceService.getAll()
                        .parallelStream()
                        .map( RaceEntity::getName )
                        .collect( Collectors.toList() ) );
        return "registrationPage";
    }
    private Cookie getCookie(Long userId ){
        Cookie id = new Cookie( "userID" , String.valueOf( userId ) );
        id.setMaxAge( ( int ) Duration.ofHours( 3 ).getSeconds() );
        return id;
    }

    @PostMapping( value = "/registration" )
    public String newUser(
            @Validated
            @ModelAttribute(value = "registrationForm")
                    RegistrationForm registrationForm , BindingResult bindingResult , Model model ,
            HttpServletResponse response ) throws IOException {
        if( bindingResult.hasErrors() ){
            model.addAttribute( "races" ,
                    raceService.getAll()
                            .parallelStream()
                            .map( RaceEntity::getName )
                            .collect( Collectors.toList() ) );
            return "registrationPage";
        }
        Long id = personService.addPerson( registrationForm );
        response.addCookie( getCookie( id ) );
        return "redirect:/persons/" + id;
    }
}
