package com.netcracker.controllers;

import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( "/groups/search" )
public class GroupSearchResultController {

    @Autowired
    GroupService groupService;
    
    @RequestMapping(value="/result", method = RequestMethod.GET)
    public String searchParams(@RequestParam(value="name", defaultValue="") String name,
                                          @RequestParam(value="ownerName", defaultValue="") String ownerName,
                               Model model ) {

        model.addAttribute( "groups" , groupService.getBySearchParams( name, ownerName ) );

        return "groupSearchResultPage";
    }
}
