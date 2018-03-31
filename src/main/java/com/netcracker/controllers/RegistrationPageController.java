package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.service.PersonService;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/index")
public class RegistrationPageController {

    @Autowired
    RaceService raceService;
    @Autowired
    PersonService personService;

    @RequestMapping(value="/registration", method= RequestMethod.POST)
    public String addPerson(@RequestParam(value="login", defaultValue="") String login,
                                   @RequestParam(value="name", defaultValue="") String name,
                                   @RequestParam(value="password", defaultValue="") String password,
                                   @RequestParam(value="race", defaultValue="") String race,
                                   @RequestParam(value="age")                   Integer age,
                                   @RequestParam(value="sex", defaultValue="") String sex,
                                   Model model){

        PersonEntity personEntity = new PersonEntity(login,password,name,raceService.getByName(race));
        personEntity.setAge(age);
        personEntity.setSex(sex);
        model.addAttribute("person",personService.addPerson(personEntity));

        return ("personPage");

    }

    @RequestMapping(value="/registration", method= RequestMethod.GET)
    public String openRegistration(@RequestParam(value="login", defaultValue="") String login,
                                   @RequestParam(value="name", defaultValue="") String name,
                                   @RequestParam(value="race", defaultValue="") String race,
                                   @RequestParam(value="age")                   Integer age,
                                   @RequestParam(value="sex", defaultValue="") String sex,
                                   Model model){

        model.addAttribute("login",login);
        model.addAttribute("name", name);
        model.addAttribute("race",race);
        model.addAttribute("age",age);
        model.addAttribute("sex",sex);

        return ("registrationPage");

    }

}
