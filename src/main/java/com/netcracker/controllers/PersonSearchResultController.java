package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonSearchResultController {

    //Here we store from where we came frome
    private String prevPage;

    @RequestMapping(value="/PersonSearchResult", method = RequestMethod.GET)
    public List<PersonEntity> searchResult(@RequestParam(value="name", defaultValue="") String name,
                                           @RequestParam(value="raceId", defaultValue="") Long raceID,
                                           @RequestParam(value="ageFrom", defaultValue="") Integer ageFrom,
                                           @RequestParam(value="ageTo", defaultValue="") Integer ageTo,
                                           @RequestParam(value="sex", defaultValue="") String sex,
                                           @RequestParam(value="prev",defaultValue = "") String pageName){
        prevPage=pageName;
        //TODO: params must be used to make request to db and show it in page
        return null;
    }
}
