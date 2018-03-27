package com.netcracker.controllers;

import com.netcracker.DAO.RaceEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonSettingsPageController {

    @RequestMapping(value="/PersonSettingsPage", method = RequestMethod.GET)
    public boolean openPage(@RequestParam(value="id", defaultValue="") Long id){

        //TODO: params must be used to open settings page
        return false;
    }

    @RequestMapping(value="/PersonSettingsPage", method = RequestMethod.POST)
    public boolean updatePerson(@RequestParam(value="login", defaultValue="") String login,
                                @RequestParam(value="password", defaultValue="") String password,
                                @RequestParam(value="name", defaultValue="") String name,
                                @RequestParam(value="race", defaultValue="") RaceEntity raceEntity,
                                @RequestParam(value="age", defaultValue="") Integer age){

        //TODO: params must be used to update user`s PersonEntity. Return true, if succes?
        return false;
    }
}
