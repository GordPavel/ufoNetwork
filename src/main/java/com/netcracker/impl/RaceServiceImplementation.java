package com.netcracker.impl;

import com.netcracker.DAO.RaceEntity;
import com.netcracker.repository.RaceRepository;
import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RaceServiceImplementation implements RaceService{

    @Autowired private RaceRepository raceRepository;

    @Override
    public RaceEntity addRace( String name ){
        RaceEntity race = new RaceEntity();
        race.setName( name );
        return raceRepository.saveAndFlush( race );
    }

    @Override
    public void delete( Long id ){
        raceRepository.deleteById( id );
    }

    @Override
    public Optional<RaceEntity> getByName( String name ){
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

    @Override
    public RaceEntity getById( Long id ){
        return raceRepository.getOne( id );
    }
}