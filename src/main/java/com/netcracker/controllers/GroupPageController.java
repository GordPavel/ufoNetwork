package com.netcracker.controllers;

import com.netcracker.DAO.MessageEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupPageController {

    @RequestMapping(value="/GroupPage", method = RequestMethod.GET)
    public boolean openPage(@RequestParam(value="id", defaultValue="") Long id) {

        //TODO: params must be used to open group`s page. Return true, if it`s user`s own page
        return false;
    }
    @RequestMapping(value="/GroupPage/PostMessage")
    public boolean postMessage(@RequestParam(value="message", defaultValue="") MessageEntity messageEntity) {

        //TODO: add message to db and redirect back to GroupPage
        return false;
    }
    @RequestMapping(value="/GroupPage/DeleteMessage")
    public boolean deleteMessage(@RequestParam(value="id", defaultValue="") Long id) {

        //TODO: delete message from db and redirect back to GroupPage
        return false;
    }
    @RequestMapping(value="/GroupPage/JoingGroup")
    public boolean joinGroup(@RequestParam(value="id", defaultValue="") Long id) {

        //TODO: add person to group and redirect to GroupPage
        return false;
    }
    @RequestMapping(value="/GroupPage/LiveGroup")
    public boolean liveGroup(@RequestParam(value="id", defaultValue="") Long id) {

        //TODO: exclude person from group and redirect to GroupPage
        return false;
    }
}
