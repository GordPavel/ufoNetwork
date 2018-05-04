package com.netcracker.impl;

import com.netcracker.DAO.*;
import com.netcracker.controllers.forms.RegistrationForm;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.repository.RaceRepository;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImplementation implements PersonService{

    @Autowired private PersonRepository personRepository;
    @Autowired private GroupRepository  groupRepository;
    @Autowired private RaceRepository   raceRepository;


    @Override
    public Optional<PersonEntity> findById( Long id , PersonLazyFields... fields ){
        return personRepository.findById( id ).map( person -> {
            List<PersonLazyFields> lazyFields = Arrays.asList( fields );
            loadFields( person , lazyFields );
            return person;
        } );
    }

    private void loadFields( PersonEntity person , List<PersonLazyFields> lazyFields ){
        if( lazyFields.contains( PersonLazyFields.MEDIA ) )
            //noinspection ConstantConditions
            person.setMedia( personRepository.getMediaById( person.getId() ).get() );
        if( lazyFields.contains( PersonLazyFields.GROUPS ) )
            person.setGroups( personRepository.getGroupsById( person.getId() ) );
        if( lazyFields.contains( PersonLazyFields.RULING_GROUPS ) )
            person.setRulingGroups( personRepository.getRulingGroupsById( person.getId() ) );
        if( lazyFields.contains( PersonLazyFields.MESSAGES ) )
            person.setMessages( personRepository.getMessagesById( person.getId() ) );
    }

    @Override
    public List<PersonEntity> listAll( PersonLazyFields... fields ){
        List<PersonLazyFields> lazyFields = Arrays.asList( fields );
        return personRepository.findAll()
                               .parallelStream()
                               .peek( person -> loadFields( person , lazyFields ) )
                               .collect( Collectors.toList() );
    }

    @Override
    public Long addPerson( RegistrationForm form ) throws IOException{
        RaceEntity race;
        if( raceRepository.findAll()
                          .parallelStream()
                          .map( RaceEntity::getName )
                          .noneMatch( java.util.function.Predicate.isEqual( form.getRace() ) ) ){
            race = new RaceEntity();
            race.setName( form.getRace() );
            raceRepository.saveAndFlush( race );
        }else{
            race = raceRepository.getByName( form.getRace() )
                                 .orElseThrow( IllegalStateException::new );
        }
        PersonEntity entity =
                new PersonEntity( form.getLogin() , form.getPass() , form.getName() , race );
        entity.setAge( Integer.parseInt(form.getAge()) );
        entity.setSex( form.getSex() );
        if( form.getImage() != null ){
            PersonMediaEntity media = new PersonMediaEntity();
            media.setImage( form.getImage().getBytes() );
            media.setPerson( entity );
            entity.setMedia( media );
        }
        return personRepository.saveAndFlush( entity ).getId();
    }

    @Override
    public List<PersonEntity> listWithSpecifications( String name , Long raceID , String ageFrom ,
                                                      String ageTo , String sex ,
                                                      PersonLazyFields... fields ){
        List<PersonLazyFields> lazyFields = Arrays.asList( fields );
        return personRepository.findAll( ( root , criteriaQuery , criteriaBuilder ) -> {
            List<Predicate> predicates = new ArrayList<>();
            if( name != null ){
                predicates.add( criteriaBuilder.like( root.get( "name" ) ,
                        name.isEmpty() ? "%" : name.replaceAll("\\*","%")
                                                   .replaceAll("\\?","_") ) );
            }
            if( raceID != null ){
                predicates.add( criteriaBuilder.equal( root.get( "race" ).get( "id" ) , raceID ) );
            }
            predicates.add( criteriaBuilder.between( root.get( "age" ) ,
                                                     ageFrom.isEmpty()  ? 0 : Integer.parseInt(ageFrom) ,
                                                     ageTo.isEmpty()  ? Integer.MAX_VALUE : Integer.parseInt(ageTo)  ) );
            if( sex != null ){
                predicates.add( criteriaBuilder.like( root.get( "sex" ) ,
                        sex.isEmpty() ? "%" : sex.replaceAll("\\*","%")
                                                 .replaceAll("\\?","_") )  );
            }
            return criteriaBuilder.and( predicates.toArray( new Predicate[]{} ) );
        } )
                               .parallelStream()
                               .peek( person -> loadFields( person , lazyFields ) )
                               .collect( Collectors.toList() );
    }

    @Override
    public void joinGroup( Long groupId , Long userId ){
//        todo Обработка ошибок
        PersonEntity user  = findById( userId , PersonLazyFields.GROUPS ).get();
        GroupEntity  group = groupRepository.findById( groupId ).get();
        user.getGroups().add( group );
        personRepository.saveAndFlush( user );
    }

    @Override
    public void leaveGroup( Long groupId , Long userId ){
//        todo Обработка ошибок
        PersonEntity user  = findById( userId , PersonLazyFields.GROUPS ).get();
        GroupEntity  group = groupRepository.findById( groupId ).get();
        user.getGroups().remove( group );
        personRepository.saveAndFlush( user );
    }
}
