package com.netcracker.service;

import com.netcracker.DAO.RaceEntity;

import java.util.List;
import java.util.Optional;

public interface RaceService{

    /**
     Add new Race to db

     @param raceEntity - Race to add

     @return - added Race
     */
    RaceEntity addRace( RaceEntity raceEntity );

    /**
     delete Race from db

     @param id - id of Race to delete
     */
    void delete( Long id );

    /**
     Searh for Race with same name

     @return
     @param name - *+name+*
     */
    Optional<RaceEntity> getByName( String name );

    /**
     Searh for Race with same id

     @param id - id

     @return
     */
    RaceEntity getById( Long id );

    /**
     List of all Races

     @return - all Races in db
     */
    List<RaceEntity> getAll();

    /**
     edit Race in db

     @param raceEntity - edited Race to put in db

     @return - edited race
     */
    RaceEntity editRace( RaceEntity raceEntity );
}
