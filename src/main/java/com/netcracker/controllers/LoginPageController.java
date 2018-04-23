package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.RaceEntity;
import com.netcracker.controllers.forms.LoginForm;
import com.netcracker.controllers.forms.LoginValidator;
import com.netcracker.controllers.forms.RegistrationForm;
import com.netcracker.controllers.forms.RegistrationValidator;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 Controller for login, registration page
 */
@Controller
@RequestMapping
public class LoginPageController{

    @Autowired private PersonService         personService;
    @Autowired private PersonRepository      personRepository;
    @Autowired private RaceService           raceService;
    @Autowired private RegistrationValidator registrationValidator;
    @Autowired private LoginValidator        loginValidator;

    //    Инициализация валидаторов
    @InitBinder( "loginForm" )
    protected void initLoginBinder( WebDataBinder binder ){
        binder.setValidator( loginValidator );
    }

    @InitBinder( "registrationForm" )
    protected void initRegistrationBinder( WebDataBinder binder ){
        binder.setValidator( registrationValidator );
    }

    //    Инициализация форм
    @ModelAttribute( "loginForm" )
    public LoginForm loginForm(){
        return new LoginForm();
    }

    @ModelAttribute( "registrationForm" )
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    //    Список всех доступных рас
    @ModelAttribute( "races" )
    public List<String> races(){
        return raceService.getAll()
                          .parallelStream()
                          .map( RaceEntity::getName )
                          .collect( Collectors.toList() );
    }

    /**
     Open log in page

     @return log in page
     */
    @GetMapping
    public String open(
            @CookieValue( name = "userID", required = false )
                    Long userId ){
        if( userId != null ) return "redirect:/persons/" + userId;
        return "loginPage";
    }

    /**
     Login.

     @param loginForm - form with login data
     @param response  - add cookie

     @return logged in users page
     */
    @PostMapping
    public String login(
            @Validated
            @ModelAttribute( "loginForm" )
                    LoginForm loginForm , BindingResult errors , HttpServletResponse response ){
        if( errors.hasErrors() ) return "loginPage";
        Long userId = personRepository.getByLogin( loginForm.getLogin() ).map( PersonEntity::getId )
//        todo Перевести на дефолтную страницу с сообщением об ошибке
                                      .orElseThrow( IllegalStateException::new );
        response.addCookie( getCookie( userId ) );
        return "redirect:/persons/" + userId;
    }

    /**
     Log  out, cleaning cookie

     @param response - response to put there no time to live cookie

     @return login page
     */
    @GetMapping( value = "/logout" )
    public String logout( HttpServletResponse response ){
        response.addCookie( getCookie( null ) );
        return "redirect:/";
    }

    /**
     Returns cookie with user id and 3 hours live time, if id isn't null, otherwise it returns
     empty cookie that'll die in next moment

     @param userId could be null to supply empty cookie

     @return cookie with user's id
     */
    private Cookie getCookie( Long userId ){
        Cookie id = new Cookie( "userID" , String.valueOf( userId ) );
        id.setMaxAge( userId != null ? ( int ) Duration.ofHours( 3 ).getSeconds() : 0 );
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
                    RegistrationForm registrationForm , BindingResult bindingResult , Model model ,
            HttpServletResponse response ) throws IOException{
        if( bindingResult.hasErrors() ) return "loginPage";

//        todo Можно добавить перехватчик ошибки чтения фотографии
        Long id = personService.addPerson( registrationForm );
        response.addCookie( getCookie( id ) );
        return "redirect:/persons/" + id;
    }

}
