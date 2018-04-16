package com.netcracker.testingRest;

import com.google.gson.GsonBuilder;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping( "/rest/race" )
public class RaceRestController{

    @Autowired RaceService raceService;

    @GetMapping( path = "/all", produces = "application/json" )
    @ResponseBody
    ResponseEntity<?> getAll(){
        return ResponseEntity.status( HttpStatus.OK )
                             .contentType( MediaType.APPLICATION_JSON_UTF8 )
                             .body( raceService.getAll()
                                               .parallelStream()
                                               .peek( race -> race.setPersons( null ) )
                                               .map( new GsonBuilder().setPrettyPrinting()
                                                                      .create()::toJson )
                                               .collect( Collectors.joining( "," , "[" , "]" ) ) );
    }

}
