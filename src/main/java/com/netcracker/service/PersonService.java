package com.netcracker.service;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.PersonLazyFields;
import com.netcracker.controllers.forms.RegistrationForm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PersonService{

    /**
     Find one person and load specified lazy properties

     @param id     - id of person
     @param fields - specified person's properties to load

     @return Optional of person if id is valid and otherwise empty Optional
     */
    Optional<PersonEntity> findById( Long id , PersonLazyFields... fields );

    /**
     List persons and load specified lazy properties

     @param fields - specified person's properties to load

     @return list of users
     */
    List<PersonEntity> listAll( PersonLazyFields... fields );


    /**
     add Person to db

     @param registrationForm - form with data of new user

     @return - returns id of new person
     */
    Long addPerson( RegistrationForm registrationForm ) throws IOException;

    /**
     Search for person by parameters

     @return - list of persons suitable for search parameters
     @param name    - *+name+* of Person
     @param raceID  - Person`s race`s id
     @param ageFrom - Person`s age >= age
     @param ageTo   - Persin`s age =< age
     @param sex     - Person`s *+sex+*
     @param fields
     */
    List<PersonEntity> listWithSpecifications( String name , Long raceID , Integer ageFrom ,
                                               Integer ageTo , String sex ,
                                               PersonLazyFields... fields );

    /**
     add person to group

     @param groupId - where must be added
     @param userId  - user`s ID
     */
    void joinGroup( Long groupId , Long userId );

    /**
     exclude person from group

     @param groupId - from where must be excluded
     @param userId  - user`s ID
     */
    void leaveGroup( Long groupId , Long userId );
}
