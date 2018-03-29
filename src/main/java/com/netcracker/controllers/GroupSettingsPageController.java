package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.RaceEntity;
import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/groups")
public class GroupSettingsPageController {

    @Autowired
    GroupService groupService;

    @RequestMapping(value="/page/{id}/settings", method = RequestMethod.GET)
    public String openPage(@PathVariable(value="id") Long id,
                           Model model){

        model.addAttribute("group",groupService.getById(id));
        return "groupSettingsPage";
    }

    @RequestMapping(value="/page/{id}/settings", params = "newName")
    public String updateGroup(@RequestParam(value="newName", defaultValue="") String newName,
                              @PathVariable(value="id") Long id,
                               Model model){

        GroupEntity toEdit = groupService.getById(id);
        toEdit.setName(newName);
        model.addAttribute("group",groupService.editGroup(toEdit));
        return "groupSettingsPage";
    }
}
