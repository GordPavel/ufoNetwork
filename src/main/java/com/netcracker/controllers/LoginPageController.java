package com.netcracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginPageController {

    @RequestMapping(value="/",method= RequestMethod.GET)
    public String login(){
        return null;
    }
}
