package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GroupSearchResultController {

    //Here we store from where we came frome
    private String prevPage;

    @RequestMapping(value="/GroupSearchPage", method = RequestMethod.GET)
    public List<GroupEntity> searchParams(@RequestParam(value="name", defaultValue="") String name,
                                          @RequestParam(value="ownerName", defaultValue="") String ownerName,
                                          @RequestParam(value="prev",defaultValue = "") String pageName) {

        prevPage=pageName;
        //TODO: params must be used to make request to db and show it in page
        return null;
    }
}
