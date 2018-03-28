package com.netcracker.controllers;

import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping( "/groups" )
public class GroupSearchPageController {

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchParams(@RequestParam(value="name", defaultValue="") String name,
                               @RequestParam(value="ownerName", defaultValue="") String ownerName,
                               Model model) {

        model.addAttribute("name",name);
        model.addAttribute("ownerName",ownerName);

        return "groupSearchPage";
    }
}
