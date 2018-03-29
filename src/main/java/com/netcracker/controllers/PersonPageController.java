package com.netcracker.controllers;

import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/persons")
public class PersonPageController {

    @Autowired
    PersonService personService;

    @RequestMapping(value="/page/{id}", method = RequestMethod.GET)
    public String openPage(@PathVariable(value="id") Long id, Model model){

        model.addAttribute("person",personService.getById(id));
        return "personPage";
    }
}
