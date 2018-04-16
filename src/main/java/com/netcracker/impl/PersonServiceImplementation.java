package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //    todo в таких запросах почитайте лучше о spring data specifications
    @Override
    public List<PersonEntity> getBySearchParams( String name , Long raceID , Integer ageFrom ,
                                                 Integer ageTo , String sex ){
        return personRepository.findAll( ( root , criteriaQuery , criteriaBuilder ) -> {
            List<Predicate> predicates = new ArrayList<>();
            if( name != null ){
                predicates.add( criteriaBuilder.like( root.get( "name" ) , name ) );
            }
            if( raceID != null ){
                predicates.add( criteriaBuilder.equal( root.get( "race" ).get( "id" ) , raceID ) );
            }
            predicates.add( criteriaBuilder.between( root.get( "age" ) ,
                                                     ageFrom != null ? ageFrom : 0 ,
                                                     ageTo != null ? ageTo : Integer.MAX_VALUE ) );
            if( sex != null ){
                predicates.add( criteriaBuilder.like( root.get( "sex" ) , sex ) );
            }
            return criteriaBuilder.and( predicates.toArray( new Predicate[]{} ) );
        } );
    }

    @Override
    public List<GroupEntity> getGroups( Long id ){
        return personRepository.getOne( id ).getGroups();
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
    public Optional<PersonEntity> getByLogin( String login ){
        return personRepository.getByLogin( login );
    }
}
