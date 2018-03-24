package com.netcracker.service;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.RaceEntity;

import java.util.List;

public interface PersonService {

    /**
     * add Person to db
     * @param personEntity - Person to add
     * @return - added Person
     */
    PersonEntity addPerson (PersonEntity personEntity);

    /**
     * delete person froom db by it ID
     * @param Id - ID of person, that must be deleted
     */
    void delete (long Id);

    /**
     * Search for person by parameters
     * @param name - *+name+* of Person
     * @param raceEntity - Person`s *+race+*
     * @param ageFrom - Person`s age >= age
     * @param ageTo - Persin`s age =< age
     * @param sex - Person`s *+sex+*
     * @return - list of persons suitable for search parameters
     */
    List<PersonEntity> getBySearchParams(String name,
                                         RaceEntity raceEntity,
                                         Integer ageFrom,
                                         Integer ageTo,
                                         String sex);

    /**
     * search for members of Group
     * @param groupEntity - Group, where Persons consistis
     * @return - list of Persons-members of group
     */
    List<PersonEntity> getByGroup (GroupEntity groupEntity);

    /**
     * get Person by ID
     * @param id - Person`s ID
     * @return Person with same ID
     */
    PersonEntity getById(Long id);

    /**
     * edit Person in db
     * @param personEntity - Person to edit
     * @return - edited Person
     */
    PersonEntity editPerson(PersonEntity personEntity);


}
