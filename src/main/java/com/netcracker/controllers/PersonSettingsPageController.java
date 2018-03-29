package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.RaceEntity;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/persons")
public class PersonSettingsPageController {

    @Autowired
    PersonService personService;

    @Autowired
    RaceServices raceServices;

    @RequestMapping(value="/page/{id}/settings", method = RequestMethod.GET)
    public String openPage(@PathVariable(value="id") Long id, Model model){

        model.addAttribute("person",personService.getById(id));
        return "presonSettingsPage";
    }

    @RequestMapping(value="/page/{id}/settings", method = RequestMethod.POST)
    public String updatePerson(@RequestParam(value="login", defaultValue="") String login,
                               @RequestParam(value="name", defaultValue="") String name,
                               @RequestParam(value="raceId", defaultValue="") String race,
                               @RequestParam(value="age") Integer age,
                               @RequestParam(value="password", defaultValue="") String password,
                               @PathVariable(value="id") Long id,
                                Model model){

        PersonEntity toEdit = personService.getById(id);

        if (!login.isEmpty()){
            toEdit.setLogin(login);
        }
        if (!name.isEmpty()){
            toEdit.setName(name);
        }
        if (!race.isEmpty()){
            toEdit.setRace(raceServices.getByName(race));
        }
        if (age!=null){
            toEdit.setAge(age);
        }
        if (!password.isEmpty()){
            toEdit.setPass(password);
        }


        model.addAttribute(personService.editPerson(toEdit));
        return "presonSettingsPage";
    }
}
