package com.netcracker.impl;

import com.netcracker.DAO.RaceEntity;
import com.netcracker.repository.RaceRepository;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceServiceImplementation implements RaceService{

    @Autowired private RaceRepository raceRepository;

    @Override
    public RaceEntity addRace( RaceEntity raceEntity ){

        return raceRepository.saveAndFlush( raceEntity );
    }

    @Override
    public void delete( Long id ){

        raceRepository.deleteById( id );
    }

    @Override
    public RaceEntity getByName( String name ){

        return raceRepository.getByName( name );
    }

    @Override
    public List<RaceEntity> getAll(){

        return raceRepository.findAll();
    }

    @Override
    public RaceEntity editRace( RaceEntity raceEntity ){

        return raceRepository.saveAndFlush( raceEntity );
    }
}