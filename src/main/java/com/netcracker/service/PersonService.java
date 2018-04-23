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

     @param name    - *+name+* of Person
     @param raceID  - Person`s race`s id
     @param ageFrom - Person`s age >= age
     @param ageTo   - Persin`s age =< age
     @param sex     - Person`s *+sex+*
     @param fields  - specify which lazy fields will be loaded

     @return - list of persons suitable for search parameters
     */
    List<PersonEntity> findBySpecifications( String name , Long raceID , Integer ageFrom ,
                                             Integer ageTo , String sex ,
                                             PersonLazyFields... fields );

    /**
     Add person to group

     @param groupId - where must be added
     @param userId  - user`s ID

     @throws IllegalStateException    - if user is already in group
     @throws IllegalArgumentException - if user or group not contains in db
     */
    void joinGroup( Long groupId , Long userId )
            throws IllegalStateException, IllegalArgumentException;

    /**
     Exclude person from group

     @param groupId - from where must be excluded
     @param userId  - user`s ID

     @throws IllegalStateException    - if user isn't in group
     @throws IllegalArgumentException - if user or group not contains in db
     */
    void leaveGroup( Long groupId , Long userId )
            throws IllegalArgumentException, IllegalStateException;
}
