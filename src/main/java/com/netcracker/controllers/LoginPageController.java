package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.controllers.forms.LoginForm;
import com.netcracker.controllers.forms.LoginValidator;
import com.netcracker.controllers.forms.RegistrationValidator;
import com.netcracker.controllers.forms.RegistrationForm;
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

/**
 Controller for login, registration page
 */
@Controller
@RequestMapping
public class LoginPageController{

    @Autowired PersonService         personService;
    @Autowired RaceService           raceService;
    @Autowired RegistrationValidator registrationValidator;
    @Autowired LoginValidator        loginValidator;

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
        return "loginPage";
    }

    /**
     Logining out, cleaning cookie

     @param response - clear cookie

     @return login page
     */
    @GetMapping( value = "/logout" )
    public String logout( HttpServletResponse response ){
        response.addCookie( getCookie( null ) );
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
        Long userId = personService.getByLogin( loginForm.getLogin() )
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
    @PostMapping( value = "/registration" )
    public String newUser(
            @Validated
            @ModelAttribute( "registrationForm" )
                    RegistrationForm registrationForm , Model model , HttpServletResponse response ,
            BindingResult bindingResult ) throws IOException{
        if( bindingResult.hasErrors() ) return "registrationPage";
//        todo Отлов ошибок
        Long id = personService.addPerson( toPersonEntity( registrationForm ) ).getId();
        response.addCookie( getCookie( id ) );
        return "redirect:/persons/" + id;
    }

    private PersonEntity toPersonEntity( RegistrationForm form ) throws IOException{
        PersonEntity entity = new PersonEntity( form.getLogin() ,
                                                form.getPass() ,
                                                form.getName() ,
                                                raceService.getByName( form.getRace() )
                                                           .orElseThrow( IllegalArgumentException::new ) );
        entity.setAge( form.getAge() );
        entity.setSex( form.getSex() );
        entity.setMedia( form.getImage().getBytes() );
        return entity;
    }
}
