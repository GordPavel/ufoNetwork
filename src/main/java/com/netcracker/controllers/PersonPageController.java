package com.netcracker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class PersonPageController {

    @RequestMapping(value="/PersonPage", method = RequestMethod.GET)
    public boolean openPage(@RequestParam(value="id", defaultValue="") Long id){

        //TODO: params must be used to open person`s page. Return true, if it`s user`s own page
        return false;
    }
}
