package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.PersonLazyFields;
import com.netcracker.DAO.PersonMediaEntity;
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

    private final PersonRepository personRepository;
    private final GroupRepository  groupRepository;
    private final RaceRepository   raceRepository;

    @Autowired
    public PersonServiceImplementation( PersonRepository personRepository ,
                                        GroupRepository groupRepository ,
                                        RaceRepository raceRepository ){
        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
        this.raceRepository = raceRepository;
    }

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
        PersonEntity entity = new PersonEntity( form.getLogin() ,
                                                form.getPass() ,
                                                form.getName() ,
                                                raceRepository.getByName( form.getRace() )
                                                              .orElseThrow( IllegalStateException::new ) );
        entity.setAge( form.getAge() );
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
    public List<PersonEntity> listWithSpecifications( String name , Long raceID , Integer ageFrom ,
                                                      Integer ageTo , String sex ,
                                                      PersonLazyFields... fields ){
        List<PersonLazyFields> lazyFields = Arrays.asList( fields );
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
        } )
                               .parallelStream()
                               .peek( person -> loadFields( person , lazyFields ) )
                               .collect( Collectors.toList() );
    }

    @Override
    public void joinGroup( Long id , Long userId ){
//        todo Обработка ошибок
        PersonEntity user  = personRepository.findById( userId ).get();
        GroupEntity  group = groupRepository.findById( id ).get();

        user.getGroups().add( group );
//        group.getUsers().add( user );

        personRepository.saveAndFlush( user );
//        groupRepository.saveAndFlush( group );
    }

    @Override
    public void leaveGroup( Long id , Long userId ){
//        todo Обработка ошибок
        PersonEntity user  = personRepository.findById( userId ).get();
        GroupEntity  group = groupRepository.findById( id ).get();

        user.getGroups().remove( group );
//        group.getUsers().remove( user );

        personRepository.saveAndFlush( user );
//        groupRepository.saveAndFlush( group );
    }
}
