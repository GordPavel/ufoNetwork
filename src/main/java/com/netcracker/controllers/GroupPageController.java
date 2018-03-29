package com.netcracker.controllers;

import com.netcracker.DAO.MessageEntity;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value="/page/{id}", method = RequestMethod.GET)
    public String openPage(@PathVariable(value="id") Long id, Model model) {

        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page/{id}", params = "message")
    public String postMessage(@RequestParam(value="message") String messageText,
                               @PathVariable(value="id") Long id,
                               Model model) {
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setToGroup(groupService.getById(id));
        messageEntity.setText(messageText);

        messageService.addMessage(messageEntity);

        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page/{id}", params = "messageId")
    public String deleteMessage(@RequestParam(value="messageId", defaultValue="") Long messageId,
                                @PathVariable(value="id") Long id,
                                Model model) {

        messageService.delete(messageId);
        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page/{id}", params = "join")
    public String joinGroup(@RequestParam(value="join", defaultValue="") Long join,
                             @PathVariable(value="id") Long id,
                             Model model) {

        personService.joinGroup(id);
        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }

    @RequestMapping(value="/page/{id}", params = "leave")
    public String leaveGroup(@RequestParam(value="leave", defaultValue="") Long leaveId,
                              @PathVariable(value="id") Long id,
                              Model model) {

        personService.leaveGroup(id);
        model.addAttribute("group",groupService.getById(id));

        return "groupPage";
    }
}
