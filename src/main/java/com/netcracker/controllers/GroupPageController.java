package com.netcracker.controllers;

import com.netcracker.DAO.MessageEntity;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/groups")
public class GroupPageController {

    @Autowired
    PersonService personService;

    @Autowired
    MessageService messageService;

    @Autowired
    GroupService groupService;

    @RequestMapping(value="/page", method = RequestMethod.GET)
    public String openPage(@RequestParam(value="id", defaultValue="") Long id, Model model) {

        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page", params = {"message","id"})
    public String postMessage(@RequestParam(value="message") MessageEntity messageEntity,
                               @RequestParam(value="id", defaultValue="") Long id,
                               Model model) {
        messageService.addMessage(messageEntity);

        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page", params = {"messageId","id"})
    public String deleteMessage(@RequestParam(value="messageId", defaultValue="") Long messageId,
                                @RequestParam(value="id", defaultValue="") Long id,
                                Model model) {

        messageService.delete(messageId);
        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page", params = {"joinId","id"})
    public String joinGroup(@RequestParam(value="joinId", defaultValue="") Long joinId,
                             @RequestParam(value="id", defaultValue="") Long id,
                             Model model) {

        personService.joinGroup(joinId);
        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page", params = {"leaveId","id"})
    public String leaveGroup(@RequestParam(value="leaveId", defaultValue="") Long leaveId,
                              @RequestParam(value="id", defaultValue="") Long id,
                              Model model) {

        personService.joinGroup(leaveId);
        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }
}
