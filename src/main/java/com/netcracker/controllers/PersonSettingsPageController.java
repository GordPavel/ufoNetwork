package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.RaceEntity;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/persons")
public class PersonSettingsPageController {

    @Autowired
    PersonService personService;

    @RequestMapping(value="/settings", method = RequestMethod.GET)
    public String openPage(@RequestParam(value="id", defaultValue="") Long id, Model model){

        model.addAttribute("person",personService.getById(id));
        return "presonSettingsPage";
    }

    @RequestMapping(value="/settings", method = RequestMethod.POST)
    public String updatePerson(@RequestParam(value="toEdit", defaultValue="") PersonEntity toEdit,
                                Model model){

        model.addAttribute(personService.editPerson(toEdit));
        return "presonSettingsPage";
    }
}
