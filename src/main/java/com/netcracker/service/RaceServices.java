package com.netcracker.service;

import com.netcracker.DAO.RaceEntity;

import java.util.List;

public interface RaceServices {

    /**
     * Add new Race to db
     * @param raceEntity - Race to add
     * @return - added Race
     */
    RaceEntity addRace(RaceEntity raceEntity);

    /**
     * delete Race from db
     * @param id - id of Race to delete
     */
    void delete(Long id);

    /**
     * Searh for Race with same name
     * @param name - *+name+*
     * @return
     */
    RaceEntity getByName(String name);

    /**
     * List of all Races
     * @return - all Races in db
     */
    List<RaceEntity> getAll();

    /**
     * edit Race in db
     * @param raceEntity - edited Race to put in db
     * @return - edited race
     */
    RaceEntity editRace(RaceEntity raceEntity);
}
