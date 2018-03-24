package com.netcracker.service;

import com.netcracker.DAO.PlanetEntity;

public interface PlanetService {

    /**
     * add new Planet to db
     * @param planetEntity - Planet to add
     * @return - added Planet
     */
    PlanetEntity addPlanet(PlanetEntity planetEntity);

    /**
     * delete Planet from db by ID
     * @param id - ID of Planet to delete
     */
    void delete(long id);
}
