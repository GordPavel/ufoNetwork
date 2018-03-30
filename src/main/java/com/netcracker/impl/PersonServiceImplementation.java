package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonEntity addPerson(PersonEntity personEntity){

        PersonEntity person = personRepository.saveAndFlush(personEntity);
        return person;
    }

    @Override
    public void delete(Long id){

        personRepository.deleteById(id);
    }

    @Override
    public List<PersonEntity> getBySearchParams(String name, Long raceID, Integer ageFrom,
                                                Integer ageTo, String sex){

        return personRepository.getBySearchParams(name, raceID, ageFrom, ageTo, sex);
    }

    @Override
    public List<GroupEntity> getGroups(Long id){

        return personRepository.getGroups(id);
    }

    @Override
    public PersonEntity getById(Long id){

        return personRepository.getOne(id);
    }

    @Override
    public PersonEntity editPerson(PersonEntity personEntity){

        return personRepository.saveAndFlush(personEntity);
    }

    @Override
    public void joinGroup(Long id){

    }
    @Override
    public void leaveGroup(Long id){

    }
}
