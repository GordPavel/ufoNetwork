package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
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

/**
 Controller for login, registration page
 */
@Controller
@RequestMapping
public class LoginPageController{

    private final PersonService         personService;
    private final PersonRepository      personRepository;
    private final RaceService           raceService;
    private final RegistrationValidator registrationValidator;
    private final LoginValidator        loginValidator;


    @Autowired
    SearchPersonsFormValidator searchPersonsFormValidator;
    @Autowired
    SearchGroupsFormValidator searchGroupsFormValidator;

    @InitBinder("searchGroupsForm")
    protected void initSearchGroupBinder( WebDataBinder binder ){ binder.setValidator( searchGroupsFormValidator ); }

    @ModelAttribute("searchGroupsForm")
    public SearchGroupsForm searchGroupsForm () { return new SearchGroupsForm();}

    @InitBinder("searchPersonsForm")
    protected void initSearchPersonBinder( WebDataBinder binder ){ binder.setValidator( searchPersonsFormValidator ); }

    @ModelAttribute("searchPersonsForm")
    public SearchPersonsForm searchPersonsForm () { return new SearchPersonsForm();}


    @Autowired
    public LoginPageController( PersonService personService , PersonRepository personRepository ,
                                RaceService raceService ,
                                RegistrationValidator registrationValidator ,
                                LoginValidator loginValidator ){
        this.personService = personService;
        this.personRepository = personRepository;
        this.raceService = raceService;
        this.registrationValidator = registrationValidator;
        this.loginValidator = loginValidator;
    }

    @InitBinder( "loginForm" )
    protected void initLoginBinder( WebDataBinder binder ){
        binder.setValidator( loginValidator );
    }

    @InitBinder( "registrationForm" )
    protected void initRegistrationBinder( WebDataBinder binder ){
        binder.setValidator( registrationValidator );
    }

    @ModelAttribute( "loginForm" )
    public LoginForm loginForm(){
        return new LoginForm();
    }

    @ModelAttribute( "registrationForm" )
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    /**
     Open login page

     @return login page
     */
    @GetMapping
    public String open( Model model ){
        model.addAttribute( "races" ,
                            raceService.getAll()
                                       .parallelStream()
                                       .map( RaceEntity::getName )
                                       .collect( Collectors.toList() ) );
        return "loginPage";
    }

    /**
     Logining out, cleaning cookie

     @param response - clear cookie

     @return login page
     */
    @GetMapping( value = "/logout" )
    public String logout( HttpServletResponse response ){
        response.addCookie( new Cookie("userID",null) );
        return "redirect:/";
    }

    /**
     Login.

     @param loginForm - form with login data
     @param model     - model to store params
     @param response  - add cookie

     @return logined users page
     */
    @PostMapping
    public String login(
            @Validated
            @ModelAttribute( "loginForm" )
                    LoginForm loginForm , BindingResult errors , Model model ,
            HttpServletResponse response ){
        if( errors.hasErrors() ){
            return "loginPage";
        }
        Long userId = personRepository.getByLogin( loginForm.getLogin() )
                                      .map( PersonEntity::getId )
                                      .orElseThrow( IllegalStateException::new );
        response.addCookie( getCookie( userId ) );
        return "redirect:/persons/" + userId;
    }

    /**
     Returns cookie with user id and 3 hours live time, if id isn't null, otherwise it returns
     empty cookie that'll die in next moment

     @param userId could be null to supply empty cookie

     @return cookie with user's id
     */
    private Cookie getCookie( Long userId ){
        Cookie id = new Cookie( "userID" , String.valueOf( userId ) );
        id.setMaxAge( ( int ) Duration.ofHours( 3 ).getSeconds() );
        return id;
    }

    /**
     register new user

     @param registrationForm - form contains all info from user
     @param model            - model to store params

     @return registered user`s page
     */
   /* @PostMapping( value = "/registration" )
    public String newUser(
            @Validated
            @ModelAttribute(value = "registrationForm")
                    RegistrationForm registrationForm , BindingResult bindingResult , Model model ,
            HttpServletResponse response ) throws IOException{
        if( bindingResult.hasErrors() ){
            model.addAttribute( "races" ,
                    raceService.getAll()
                            .parallelStream()
                            .map( RaceEntity::getName )
                            .collect( Collectors.toList() ) );
            return "loginPage";
        }
        Long id = personService.addPerson( registrationForm );
        response.addCookie( getCookie( id ) );
        return "redirect:/persons/" + id;
    }*/

}
