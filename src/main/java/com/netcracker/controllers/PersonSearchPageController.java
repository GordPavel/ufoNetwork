package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.controllers.forms.ChangeFormValidator;
import com.netcracker.controllers.forms.SearchPersonsForm;
import com.netcracker.controllers.forms.SearchPersonsFormValidator;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping
public class PersonSearchPageController{

    @Autowired PersonService personService;
    @Autowired RaceService raceService;
    @Autowired SearchPersonsFormValidator searchPersonsFormValidator;

    @InitBinder("searchPersonsForm")
    protected void initChangeBinder( WebDataBinder binder ){ binder.setValidator( searchPersonsFormValidator ); }

    @ModelAttribute("searchPersonsForm")
    public SearchPersonsForm searchPersonsForm () { return new SearchPersonsForm();}


  /*  /**
     Open user`s search page with pre-filled fields

     @param name    - user`s name
     @param raceID  - user`s race
     @param ageFrom - minimum user`s age
     @param ageTo   - maximum user`s age
     @param sex     - user`s sex
     @param model   - model to store params

     @return search page
     */
   /* @GetMapping( value = "/search" )
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
                    String sex ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId , Model model ){

        if( userId == null ){
            return "redirect:/";
        }

        model.addAttribute( "name" , name );
        model.addAttribute( "raceId" , raceID );
        model.addAttribute( "ageFrom" , ageFrom );
        model.addAttribute( "ageTo" , ageTo );
        model.addAttribute( "sex" , sex );
        return "personSearchPage";
    }

    /**
     Open user`s search result page

     @param name    - user`s name
     @param raceID  - user`s race
     @param ageFrom - minimum user`s age
     @param ageTo   - maximum user`s age
     @param sex     - user`s sex
     @param model   - model to store params

     @return search result page
     */
   /* @GetMapping( value = "/search/result" )
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
                    String sex ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId , Model model ){

        Pattern p           = Pattern.compile( "[\\d\\s-_\\*\\?]+" );
        Matcher nameMatcher = p.matcher( name );
        Matcher sexMatcher  = p.matcher( sex );

        if( !p.matcher( name ).matches() ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "unacceptable name" );
            return "personSearchPage";
        }
        if( !p.matcher( sex ).matches() ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "unacceptable sex" );
            return "personSearchPage";
        }
        if( ageFrom > ageTo ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "incorrect age parameters" );
            return "personSearchPage";
        }
        if( raceService.getById( raceID ) == null ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "incorrect race" );
            return "personSearchPage";
        }

        if( userId == null ){
            return "redirect:/";
        }
        model.addAttribute( "persons" ,
                            personService.listWithSpecifications( name ,
                                                                  raceID ,
                                                                  ageFrom ,
                                                                  ageTo ,
                                                                  sex ) );

        return "searchUserPage";
    }*/
    @PostMapping( value = "/srcpersons" )
    public String searchPersons (  @Validated
                                   @ModelAttribute( "searchPersonsForm" ) SearchPersonsForm searchPersonsForm ,
                                   Model model ,
                                   HttpServletResponse response ,
                                   BindingResult bindingResult){
        if( bindingResult.hasErrors() ) return "redirect:/";

        Long raceID = raceService.getByName( searchPersonsForm.getRace()).get().getId();

        model.addAttribute( "persons" ,
                                personService.listWithSpecifications( searchPersonsForm.getName(),
                                                                      raceID ,
                                                                      Integer.parseInt( searchPersonsForm.getAgeFrom() ),
                                                                      Integer.parseInt( searchPersonsForm.getAgeTo() ),
                                                                      searchPersonsForm.getSex() ) );

        return "searchUserPage";
    }

   /* @GetMapping( value = "/search/result" )
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
                    String sex ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId , Model model ){

        Pattern p           = Pattern.compile( "[\\d\\s-_\\*\\?]+" );
        Matcher nameMatcher = p.matcher( name );
        Matcher sexMatcher  = p.matcher( sex );

        if( !p.matcher( name ).matches() ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "unacceptable name" );
            return "personSearchPage";
        }
        if( !p.matcher( sex ).matches() ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "unacceptable sex" );
            return "personSearchPage";
        }
        if( ageFrom > ageTo ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "incorrect age parameters" );
            return "personSearchPage";
        }
        if( raceService.getById( raceID ) == null ){
            //TODO: error message implementation
            model.addAttribute( "error_message" , "incorrect race" );
            return "personSearchPage";
        }

        if( userId == null ){
            return "redirect:/";
        }
        model.addAttribute( "persons" ,
                            personService.listWithSpecifications( name ,
                                                                  raceID ,
                                                                  ageFrom ,
                                                                  ageTo ,
                                                                  sex ) );

        return "searchUserPage";
    }*/


}
