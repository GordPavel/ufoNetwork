package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping( "/groups" ) 
public class GroupSearchResultController {

    //Here we store from where we came frome
    private String prevPage;

    @Autowired 
    GroupService groupService;
    
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchParams(@RequestParam(value="name", defaultValue="") String name,
                                          @RequestParam(value="ownerName", defaultValue="") String ownerName,
                                          @RequestParam(value="prev",defaultValue = "") String pageName , 
                               Model model ) {

        prevPage=pageName;
        model.addAttribute( "groups" , groupService.getBySearchParams( name, owner ) );
        //TODO: params must be used to make request to db and show it in page
        return "groupPage";
    }
}
