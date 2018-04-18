package com.netcracker.service;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;

import java.util.List;
import java.util.Optional;

public interface PersonService{

    /**
     add Person to db

     @param personEntity - Person to add

     @return - added Person
     */
    PersonEntity addPerson( PersonEntity personEntity );

    /**
     delete person froom db by it ID

     @param Id - ID of person, that must be deleted
     */
    void delete( Long Id );

    /**
     Search for person by parameters

     @param name    - *+name+* of Person
     @param raceID  - Person`s race`s id
     @param ageFrom - Person`s age >= age
     @param ageTo   - Persin`s age =< age
     @param sex     - Person`s *+sex+*

     @return - list of persons suitable for search parameters
     */
    List<PersonEntity> getBySearchParams( String name , Long raceID , Integer ageFrom ,
                                          Integer ageTo , String sex );

    /**
     search for Group where is member with specific id

     @param Id - id of user

     @return - list of Persons-members of group
     */
    List<GroupEntity> getGroups( Long Id );

    /**
     get Person by ID

     @param id - Person`s ID

     @return Person with same ID
     */
    Optional<PersonEntity> getById( Long id );

    /**
     edit Person in db

     @param personEntity - Person to edit

     @return - edited Person
     */
    PersonEntity editPerson( PersonEntity personEntity );

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

    /**
     Find one user by login

     @param login login of user

     @return person entity from DB
     */
    Optional<PersonEntity> getByLogin( String login );
}
