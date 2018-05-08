package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.PersonLazyFields;
import com.netcracker.controllers.forms.*;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping( value = "/persons" )
public class PersonPageController{

    @Autowired PersonService    personService;
    @Autowired PersonRepository personRepository;
    @Autowired GroupRepository  groupRepository;
    @Autowired RaceService      raceService;
    @Autowired
    GroupCreateValidator groupCreateValidator;

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

    @InitBinder( "groupCreateForm" )
    protected void initGroupControlBinder( WebDataBinder binder ){
        binder.setValidator( groupCreateValidator );
    }

    @ModelAttribute( "groupCreateForm" )
    public GroupCreateForm groupCreateForm(){
        return new GroupCreateForm();
    }
    /**
     open user`s page

     @param id    - user`s id, path variable
     @param model - model to store param

     @return - user`s page
     */
    @GetMapping( value = "/{id}" )
    public String personPage(
            @PathVariable( value = "id" )
                    Long id ,
            @CookieValue( name = "userID" )
                    Long userId , Model model ){
        if( userId == null ) return "redirect:/";
        Optional<PersonEntity> one = personService.findById( id , PersonLazyFields.GROUPS );
        if( one.isPresent() ){
            if (one.get().getDeleted()){
                return "redirect:/persons/"+userId;
            }
            model.addAttribute( "person" , one.get() );
            if( id.equals( userId ) ){
            }
            return "personPage";
        }else{
            return "redirect:/";
        }
    }

    @PostMapping( value = "/search" )
    public String searchPersons (  @ModelAttribute( "searchPersonsForm" ) SearchPersonsForm searchPersonsForm ,
                                   Model model ){

        Long raceID = null;
        if (!searchPersonsForm.getRace().isEmpty()) {
            raceID = raceService.getByName(searchPersonsForm.getRace()).get().getId();
        }
        model.addAttribute( "persons" ,
                personService.listWithSpecifications( searchPersonsForm.getName(),
                        raceID ,
                        searchPersonsForm.getAgeFrom() ,
                        searchPersonsForm.getAgeTo() ,
                        searchPersonsForm.getSex() ) );
        model.addAttribute("name",searchPersonsForm.getName());
        model.addAttribute("race",searchPersonsForm.getRace());
        model.addAttribute("ageFrom",searchPersonsForm.getAgeFrom());
        model.addAttribute("ageTo",searchPersonsForm.getAgeTo());
        model.addAttribute("sex",searchPersonsForm.getSex());
        return "searchUserPage";
    }

    @PostMapping( value = "/search.json" )
    public @ResponseBody
    ValidationResponse validPersonSearch (  @Validated
                                   @ModelAttribute( "searchPersonsForm" ) SearchPersonsForm searchPersonsForm ,
                                   BindingResult bindingResult ,
                                   Model model ){
        ValidationResponse res = new ValidationResponse();
        if( bindingResult.hasErrors() ) {
            res.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
            for (FieldError objectError : allErrors) {
                errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getDefaultMessage()));
            }
            res.setErrorMessageList(errorMesages);
        } else {
            res.setStatus("SUCCESS");
        }
        return res;
    }

}
