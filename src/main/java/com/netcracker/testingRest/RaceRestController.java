package com.netcracker.testingRest;

import com.netcracker.DAO.RaceEntity;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/rest/race" )
public class RaceRestController{

    @Autowired RaceService raceService;

    @GetMapping( "/all" )
    List<RaceEntity> getAll(){
        return raceService.getAll();
    }

}
