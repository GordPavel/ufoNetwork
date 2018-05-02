package com.netcracker.controllers;


import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.RaceEntity;
import com.netcracker.controllers.forms.ChangeForm;
import com.netcracker.controllers.forms.ChangeFormValidator;
import com.netcracker.controllers.forms.ChangePasswdForm;
import com.netcracker.controllers.forms.ChangePasswdFormValidator;
import com.netcracker.repository.PersonRepository;
import com.netcracker.repository.RaceRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class SettingsPageController {

    @Autowired RaceRepository raceRepository;
    @Autowired PersonRepository personRepository;
    @Autowired PersonService personService;
    @Autowired RaceService raceService;
    @Autowired ChangeFormValidator changeFormValidator;
    @Autowired ChangePasswdFormValidator changePasswdFormValidator;

    private Long userId;

    @InitBinder("changeForm")
    protected void initChangeBinder( WebDataBinder binder ){ binder.setValidator( changeFormValidator ); }

    @InitBinder("changePasswdForm")
    protected void initChangePasswdBinder( WebDataBinder  binder ){ binder.setValidator( changePasswdFormValidator );}

    @ModelAttribute("changeForm")
    public ChangeForm changeForm () { return new ChangeForm();}

    @ModelAttribute("changePasswdForm")
    public ChangePasswdForm changePasswdForm () { return new ChangePasswdForm();}

    @GetMapping( value = "persons/{id}/settings" )
    public String settingsPage( @PathVariable( value = "id" )   Long id ,
                                @CookieValue( name = "userID" ) Long userId ,
                                Model model ){
//        todo: пользователь не авторизовался
        if( userId == null ) return "redirect:/";
        Optional<PersonEntity> person = personRepository.findById( id );
        if( person.isPresent() ){
            this.userId = id;
            model.addAttribute( "person" , person.get() );
            model.addAttribute( "races" ,
                    raceService.getAll()
                            .parallelStream()
                            .map( RaceEntity::getName )
                            .collect( Collectors.toList() ) );
            return "settingsPage";
        }else{
//            todo:
            return "redirect:/";
        }
    }

    @PostMapping( value = "persons/{id}/settings" )
    public String changeUser(@Validated
                             @ModelAttribute( "changeForm" )ChangeForm chForm ,
                             Model model ,
                             HttpServletResponse response ,
                             BindingResult bindingResult,
                             @PathVariable(value = "id") Long id ){
        if( bindingResult.hasErrors() ) return "settingsPage";
//        todo Отлов ошибок
        if ( !personRepository.getByLogin(chForm.getLogin()).isPresent() ||
                personRepository.getByLogin(chForm.getLogin()).get().getId().equals(id) ) {
            PersonEntity person = personRepository.saveAndFlush(toChangePerson(id, chForm));
            //       PersonEntity personEntity = personService.editPerson( toChangePerson( id, chForm ) );
            model.addAttribute("person", person);

            return "redirect:/persons/" + id + "/settings";
        }
        else {
            return "redirect:/";
        }
    }


    @PostMapping( value = "/pass" )
    public String changeUserPasswd(@Validated
                                   @ModelAttribute( "changePasswdForm" )ChangePasswdForm chPassForm ,
                                   Model model ,
                                   HttpServletResponse response ,
                                   BindingResult bindingResult){
        if( bindingResult.hasErrors() ) return "settingsPage";
//        todo Отлов ошибок

        PersonEntity person = personRepository.saveAndFlush( toChangePasswd( this.userId, chPassForm ) );
        //       PersonEntity personEntity = personService.editPerson( toChangePerson( id, chForm ) );
        model.addAttribute("person", person);
        return "redirect:/persons/" + this.userId ;

    }


    private PersonEntity toChangePerson(Long id, ChangeForm chForm ) {

//      Optional<PersonEntity> entity = personService.getById( id );

        Optional<PersonEntity> personEntity = personRepository.findById( id );
        personEntity.get().setLogin( chForm.getLogin() );
        personEntity.get().setAge( Integer.parseInt(chForm.getAge()) );
        personEntity.get().setName( chForm.getName() );
        personEntity.get().setRace( raceService.getByName( chForm.getRace() ).get() );
        personEntity.get().setSex( chForm.getSex() );

        return personEntity.get();
    }

    private PersonEntity toChangePasswd(Long id, ChangePasswdForm chPassForm ){

//      Optional<PersonEntity> personEntity = personService.getById( id );

        Optional<PersonEntity> personEntity = personRepository.findById( id );

        personEntity.get().setPass( chPassForm.getNewPasswd() );
        return personEntity.get();
    }



}
