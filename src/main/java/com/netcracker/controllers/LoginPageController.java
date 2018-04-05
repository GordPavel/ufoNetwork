package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * Controller for login, registration page
 */
@Controller
@RequestMapping( "/" )
public class LoginPageController{

    @Autowired
    PersonService personService;
    @Autowired
    RaceService raceService;

    /**
     * Open login page
     * @param login - user`s login to pre-filling page
     * @param model - model to store params
     * @return login page
     */
    @GetMapping( )
    public String open(
            @RequestParam( value = "login", defaultValue = "" )
                    String login , Model model ){

        model.addAttribute( "login" , login );
        return "loginPage";
    }

    /**
     * Logining out, cleaning cookie
     * @param response - clear cookie
     * @return login page
     */
    @GetMapping(value = "/logout")
    public String logout(
          HttpServletResponse response ){

        response.addCookie( new Cookie( "userID" , null ) );
        return "loginPage";
    }

    /**
     * Login.
     * @param login - user`s login
     * @param password - user`s password
     * @param model - model to store params
     * @param response - add cookie
     * @return logined users page
     */
    @PostMapping( )
    public String login(
            @RequestParam( value = "login", defaultValue = "" )
                    String login ,
            @RequestParam( value = "password", defaultValue = "" )
                    String password , Model model , HttpServletResponse response ){

        PersonEntity personEntity = personService.getByLogin( login );

        if( password.equals( personEntity.getPass() ) ){
            model.addAttribute( "person" , personEntity );

//            Для автоматического удаления кук через 3 часа
            Cookie id = new Cookie( "userID" , personEntity.getId().toString() );
            id.setMaxAge( ( int ) Duration.ofHours( 3 ).getSeconds() );
            response.addCookie( id );

            return "personPage";
        }else{
//            todo : Настроить отображение ошибки входа
            return "redirect:/";
        }
    }

    /**
     * open registration page with pre-filled fields
     * @param login - user`s login
     * @param name - user`s name
     * @param race - user`s race
     * @param age - user`s age
     * @param sex - user`s sex
     * @param model - model to store params
     * @return registration page
     */
    @GetMapping( value = "/registration")
    public String openRegistration(
            @RequestParam( value = "login", defaultValue = "" )
                    String login ,
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "race", defaultValue = "" )
                    String race ,
            @RequestParam( value = "age" )
                    Integer age ,
            @RequestParam( value = "sex", defaultValue = "" )
                    String sex , Model model ){

        model.addAttribute( "login" , login );
        model.addAttribute( "name" , name );
        model.addAttribute( "race" , race );
        model.addAttribute( "age" , age );
        model.addAttribute( "sex" , sex );

        return ( "registrationPage" );

    }

    /**
     * register new user
     * @param login - user`s login
     * @param name - user`s name
     * @param race - user`s race
     * @param age - user`s age
     * @param sex - user`s sex
     * @param model - model to store params
     * @return registred user`s page
     */
    @PostMapping( value = "/registration")
    public String addPerson(
            @RequestParam( value = "login", defaultValue = "" )
                    String login ,
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "password", defaultValue = "" )
                    String password ,
            @RequestParam( value = "race", defaultValue = "" )
                    String race ,
            @RequestParam( value = "age" )
                    Integer age ,
            @RequestParam( value = "sex", defaultValue = "" )
                    String sex , Model model ){

        PersonEntity personEntity =
                new PersonEntity( login , password , name , raceService.getByName( race ) );
        personEntity.setAge( age );
        personEntity.setSex( sex );
        model.addAttribute( "person" , personService.addPerson( personEntity ) );

        return ( "personPage" );

    }
}
