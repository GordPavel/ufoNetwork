package com.netcracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GroupSearchPageController {

    @RequestMapping(value="/GroupSearchPage", method = RequestMethod.GET)
    public String searchParams(@RequestParam(value="name", defaultValue="") String name,
                                           @RequestParam(value="ownerName", defaultValue="") String ownerName) {

        //TODO: params must be used to fill fields on search page
        return null;
    }
}
