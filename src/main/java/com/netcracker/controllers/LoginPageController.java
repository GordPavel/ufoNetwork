package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/index")
public class LoginPageController {

    @Autowired
    PersonService personService;

    @RequestMapping(method= RequestMethod.GET)
    public String open(@RequestParam(value="login", defaultValue="") String login,
                       Model model,
                       HttpServletResponse response){

        model.addAttribute("login",login);
        response.addCookie(new Cookie("userID", null));
        return "loginPage";
    }
    @RequestMapping(method= RequestMethod.POST)
    public String login(@RequestParam(value="login", defaultValue="") String login,
                        @RequestParam(value="password", defaultValue="") String password,
                        Model model,
                        HttpServletResponse response){

        PersonEntity personEntity = personService.loginPerson(login, password);

        model.addAttribute("person",personEntity);

        response.addCookie(new Cookie("userID", personEntity.getId().toString()));

        return "personPage";
    }
}
