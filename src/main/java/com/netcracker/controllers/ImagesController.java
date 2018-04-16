package com.netcracker.controllers;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/user-{id}/image" )
public class ImagesController{
    @Autowired private PersonRepository   personRepository;
    @Autowired private ApplicationContext context;

    @GetMapping( produces = "image/png" )
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    Resource getImage(
            @PathVariable( "id" )
                    Long id ){
        return personRepository.findById( id ).map( PersonEntity::getMedia ).<Resource> map(
                ByteArrayResource::new ).orElse( context.getResource( "/resources/images/user.png" ) );
    }
}
