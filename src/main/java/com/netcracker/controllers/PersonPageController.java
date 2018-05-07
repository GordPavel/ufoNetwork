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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String searchPersons (  @Validated
                                   @ModelAttribute( "searchPersonsForm" ) SearchPersonsForm searchPersonsForm ,
                                   BindingResult bindingResult ,
                                   Model model ,
                                   HttpServletResponse response ,
                                   HttpServletRequest request ,
                                   RedirectAttributes attr){
        if( bindingResult.hasErrors() ) {
            String referer = request.getHeader("Referer");
            attr.addFlashAttribute("org.springframework.validation.BindingResult.searchPersonsForm", bindingResult);
            attr.addFlashAttribute("searchPersonsForm", searchPersonsForm);
            return "redirect:"+referer;
        }

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

//    /**
//     open settings page
//
//     @param id    - user ID, path variable
//     @param model - model to store params
//
//     @return settings page
//     */
//    @GetMapping( value = "/{id}/settings" )
//    public String settingsPage(
//            @PathVariable( value = "id" )
//                    Long id ,
//            @CookieValue( name = "userID" )
//                    Long userId , Model model ){
//        if( !id.equals( userId ) ) return "forward:/";
//        model.addAttribute( "person" , personService.getById( id ) );
//        return "personSettingsPage";
//    }
//
//    /**
//     page to change password
//
//     @return change password page
//     */
//    @GetMapping( value = "/{id}/settings/pass" )
//    public String openPassPage(
//            @PathVariable( value = "id" )
//                    Long id ,
//            @CookieValue( name = "userID" )
//                    Long userId ){
//        if( !id.equals( userId ) ) return "forward:/";
//        return "personSettingsPassPage";
//    }
//
//    /**
//     update user`s params
//
//     @param login - new login
//     @param name  - new name
//     @param race  - new race
//     @param age   - new age
//     @param id    - user`s ID, path variable
//     @param model - model to store params
//
//     @return setting`s page
//     */
//    @PostMapping( value = "/{id}/settings" )
//    public String updatePerson(
//            @RequestParam( value = "login", defaultValue = "" )
//                    String login ,
//            @RequestParam( value = "name", defaultValue = "" )
//                    String name ,
//            @RequestParam( value = "raceId", defaultValue = "" )
//                    String race ,
//            @RequestParam( value = "age" )
//                    Integer age ,
//            @PathVariable( value = "id" )
//                    Long id ,
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long userId , Model model ){
//
//        if( !id.equals( userId ) ) return "redirect:/";
//        PersonEntity toEdit = personService.getById( id );
//
//        if( !login.isEmpty() ){
//            toEdit.setLogin( login );
//        }
//        if( !name.isEmpty() ){
//            toEdit.setName( name );
//        }
//        if( !race.isEmpty() ){
//            toEdit.setRace( raceService.getByName( race ) );
//        }
//        if( age != null ){
//            toEdit.setAge( age );
//        }
//
//        model.addAttribute( "person" , personService.editPerson( toEdit ) );
//        return "personSettingsPage";
//    }
//
//    /**
//     Updating password
//
//     @param password    - old password
//     @param newPassword - new password
//     @param id          - user`s id, path variable
//     */
//    @PostMapping( value = "/{id}/settings/pass" )
//    public String updatePersonPass(
//            @RequestParam( value = "password", defaultValue = "" )
//                    String password ,
//            @RequestParam( value = "newPassword", defaultValue = "" )
//                    String newPassword ,
//            @PathVariable( value = "id" )
//                    Long id ,
//            @CookieValue( name = "userID", defaultValue = "" )
//                    Long userId , Model model ){
//
//        if( !id.equals( userId ) ) return "redirect:/";
//        PersonEntity toEdit = personService.getById( id );
//
//        if( toEdit.getPass().equals( password ) ){
//            toEdit.setPass( newPassword );
//        }
//
//        model.addAttribute( "person" , personService.editPerson( toEdit ) );
//        return "personSettingsPage";
//    }
}
