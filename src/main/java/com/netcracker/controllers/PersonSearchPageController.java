package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonSearchPageController {

    @RequestMapping(value="/PersonSearchPage", method = RequestMethod.GET)
    public String searchParams(@RequestParam(value="name", defaultValue="") String name,
                                           @RequestParam(value="raceId", defaultValue="") Long raceID,
                                           @RequestParam(value="ageFrom", defaultValue="") Integer ageFrom,
                                           @RequestParam(value="ageTo", defaultValue="") Integer ageTo,
                                           @RequestParam(value="sex", defaultValue="") String sex){

        //TODO: params must be used to fill fields on search page
        return null;
    }
}
