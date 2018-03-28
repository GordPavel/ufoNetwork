package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.RaceEntity;
import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/groups")
public class GroupSettingsPageController {

    @Autowired
    GroupService groupService;
    @RequestMapping(value="/settings", method = RequestMethod.GET)
    public String openPage(@RequestParam(value="id", defaultValue="") Long id,
                           Model model){

        model.addAttribute("group",groupService.getById(id));
        return "groupSettingsPage";
    }

    @RequestMapping(value="/settings", params = "toEdit")
    public String updateGroup(@RequestParam(value="toEdit", defaultValue="") GroupEntity toEdit,
                               Model model){

        model.addAttribute("group",groupService.editGroup(toEdit));
        return "groupSettingsPage";
    }
}
