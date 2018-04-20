package com.netcracker.controllers;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.GroupMediaEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.PersonMediaEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/" )
public class ImagesController{
    @Autowired private PersonRepository   personRepository;
    @Autowired private ApplicationContext context;
    @Autowired private GroupRepository    groupRepository;

    @GetMapping(value = "/user-{id}/image", produces = "image/png" )
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    Resource getImage(
            @PathVariable( "id" )
                    Long id ){
        return personRepository.findById( id )
                               .map( PersonEntity::getMedia )
                               .map( PersonMediaEntity::getImage ).<Resource> map( ByteArrayResource::new )
                .orElse( context.getResource( "/resources/images/user.png" ) );
    }

    @GetMapping(value = "/group-{id}/image", produces = "image/png" )
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    Resource getGroupImage(
            @PathVariable( "id" )
                    Long id ){
        return groupRepository.findById( id )
                .map( GroupEntity::getMedia )
                .map( GroupMediaEntity::getImage ).<Resource> map( ByteArrayResource::new )
                .orElse( context.getResource( "/resources/images/group.png" ) );
    }
}
