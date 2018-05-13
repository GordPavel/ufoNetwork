package com.netcracker.controllers;

import com.netcracker.DAO.RaceEntity;
import com.netcracker.controllers.forms.*;
import com.netcracker.service.GroupService;
import com.netcracker.service.PersonService;
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
import java.io.IOException;


/*@Controller
@RequestMapping()
public class CreationGroupController {

    @Autowired
    GroupService groupService;
    @Autowired
    PersonService personService;
    @Autowired
    GroupCreateValidator groupCreateValidator;
    @Autowired
    SearchPersonsFormValidator searchPersonsFormValidator;
    @Autowired
    SearchGroupsFormValidator searchGroupsFormValidator;

    @InitBinder( "groupCreateForm" )
    protected void initGroupCreateBinder( WebDataBinder binder ){
        binder.setValidator( groupCreateValidator );
    }

    @ModelAttribute( "groupCreateForm" )
    public GroupCreateForm groupCreateForm(){
        return new GroupCreateForm();
    }

    @InitBinder("searchGroupsForm")
    protected void initSearchGroupBinder( WebDataBinder binder ){ binder.setValidator( searchGroupsFormValidator ); }

    @ModelAttribute("searchGroupsForm")
    public SearchGroupsForm searchGroupsForm () { return new SearchGroupsForm();}

    @InitBinder("searchPersonsForm")
    protected void initSearchPersonBinder( WebDataBinder binder ){ binder.setValidator( searchPersonsFormValidator ); }

    @ModelAttribute("searchPersonsForm")
    public SearchPersonsForm searchPersonsForm () { return new SearchPersonsForm();}

    @GetMapping(value = "/create")
    public String open( Model model ){

        return "createGroupPage";
    }


    @PostMapping( value = "/create" )
    public String createGroup(
            @Validated
            @ModelAttribute( "groupCreateForm" )
                    GroupCreateForm groupCreateForm , BindingResult errors , Model model ,
            HttpServletResponse response ,
            HttpServletRequest request ,
            RedirectAttributes attr ,
            @CookieValue( name = "userID", defaultValue = "" )
                    Long userId ) throws IOException {


        if( userId == null ){
            return "redirect:/";
        }

        if( errors.hasErrors() ){
            String referer = request.getHeader("Referer");
            attr.addFlashAttribute("org.springframework.validation.BindingResult.groupCreateForm", errors);
            attr.addFlashAttribute("groupCreateForm", groupCreateForm);
            return "redirect:"+ referer;
        }

        Long groupId = groupService.addGroup(groupCreateForm,userId);
        personService.joinGroup(groupId,userId);

        return "redirect:/groups/"+ groupId;
    }
}*/
