package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( value = "/persons" )
public class PersonPageController{

    @Autowired
    PersonService personService;

    @Autowired
    RaceService raceService;

    /**
     * open user`s page
     * @param id - user`s id, path variable
     * @param model - model to store param
     * @return - user`s page
     * //TODO проверка, зашёл ли пользователь к себе на страницу
     */
    @GetMapping( value = "/{id}" )
    public String personPage(
            @PathVariable( value = "id" )
                    Long id , Model model ){
        model.addAttribute( "person" , personService.getById( id ) );
        return "personPage";
    }

    /**
     * open settings page
     * @param id - user ID, path variable
     * @param model - model to store params
     * @return settings page
     */
    @GetMapping( value = "/{id}/settings" )
    public String openPage(
            @PathVariable( value = "id" )
                    Long id , Model model ){

        model.addAttribute( "person" , personService.getById( id ) );
        return "presonSettingsPage";
    }

    /**
     * page to change password
     * @return change password page
     */
    @GetMapping( value = "/{id}/settings/pass" )
    public String openPassPage(){

        return "presonSettingsPassPage";
    }

    /**
     * update user`s params
     * @param login - new login
     * @param name - new name
     * @param race - new race
     * @param age - new age
     * @param id - user`s ID, path variable
     * @param model - model to store params
     * @return setting`s page
     */
    @PostMapping( value = "/{id}/settings" )
    public String updatePerson(
            @RequestParam( value = "login", defaultValue = "" )
                    String login ,
            @RequestParam( value = "name", defaultValue = "" )
                    String name ,
            @RequestParam( value = "raceId", defaultValue = "" )
                    String race ,
            @RequestParam( value = "age" )
                    Integer age ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        PersonEntity toEdit = personService.getById( id );

        if( !login.isEmpty() ){
            toEdit.setLogin( login );
        }
        if( !name.isEmpty() ){
            toEdit.setName( name );
        }
        if( !race.isEmpty() ){
            toEdit.setRace( raceService.getByName( race ) );
        }
        if( age != null ){
            toEdit.setAge( age );
        }

        model.addAttribute( "person" , personService.editPerson( toEdit ) );
        return "presonSettingsPage";
    }

    /**
     * Updating password
     * @param password - old password
     * @param newPassword - new password
     * @param id - user`s id, path variable
     * @param model
     * @return
     */
    @PostMapping( value = "/{id}/settings/pass" )
    public String updatePersonPass(
            @RequestParam( value = "password", defaultValue = "" )
                    String password ,
            @RequestParam( value = "newPassword", defaultValue = "" )
                    String newPassword ,
            @PathVariable( value = "id" )
                    Long id , Model model ){

        PersonEntity toEdit = personService.getById( id );

        if( toEdit.getPass().equals( password ) ){
            toEdit.setPass( newPassword );
        }

        model.addAttribute( "person" , personService.editPerson( toEdit ) );
        return "presonSettingsPage";
    }
}
