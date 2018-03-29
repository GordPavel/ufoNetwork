package com.netcracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index")
public class LoginPageController {

    @RequestMapping(method= RequestMethod.GET)
    public String open(){
        return "loginPage";
    }
    @RequestMapping(method= RequestMethod.POST)
    public String login(){
        return "personPage";
    }
}
