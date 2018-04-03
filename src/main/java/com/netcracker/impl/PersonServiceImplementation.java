package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService{

    @Autowired private PersonRepository personRepository;

    @Autowired private GroupRepository groupRepository;

    @Override
    public PersonEntity addPerson( PersonEntity personEntity ){

        return personRepository.saveAndFlush( personEntity );
    }

    @Override
    public void delete( Long id ){

        personRepository.deleteById( id );
    }

    @Override
    public List<PersonEntity> getBySearchParams( String name , Long raceID , Integer ageFrom ,
                                                 Integer ageTo , String sex ){

        return personRepository.getBySearchParams( name , raceID , ageFrom , ageTo , sex );
    }

    @Override
    public List<GroupEntity> getGroups( Long id ){

        return personRepository.getGroups( id );
    }

    @Override
    public PersonEntity getById( Long id ){

        return personRepository.getOne( id );
    }

    @Override
    public PersonEntity editPerson( PersonEntity personEntity ){

        return personRepository.saveAndFlush( personEntity );
    }

    @Override
    public void joinGroup( Long id , Long userId ){

        PersonEntity user  = personRepository.getOne( userId );
        GroupEntity  group = groupRepository.getOne( id );

        user.getGroups().add( group );
        group.getUsers().add( user );

        personRepository.saveAndFlush( user );
        groupRepository.saveAndFlush( group );

    }

    @Override
    public void leaveGroup( Long id , Long userId ){

        PersonEntity user  = personRepository.getOne( userId );
        GroupEntity  group = groupRepository.getOne( id );

        user.getGroups().remove( group );
        group.getUsers().remove( user );

        personRepository.saveAndFlush( user );
        groupRepository.saveAndFlush( group );

    }

    @Override
    public PersonEntity loginPerson( String login , String password ){
        return personRepository.login( login , password );
    }

    @Override
    public PersonEntity getByLogin( String login ){
        return personRepository.getByLogin( login );
    }
}
