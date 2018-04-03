package com.netcracker.service;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;

import java.util.List;

public interface GroupService{

    /**
     add Group to db

     @param groupEntity - group to add
     */
    GroupEntity addGroup( GroupEntity groupEntity );

    /**
     delete Group by ID

     @param id - ID of group to delete
     */
    void delete( Long id );

    /**
     search for Groups with specific owner name

     @param ownerName - *+ownerName+* to search
     @param name      - *+name+* to search

     @return - list of all groups suitable for search parameters
     */
    List<GroupEntity> getBySearchParams( String name , String ownerName );

    /**
     search for Groups with specific members

     @param id - id of group

     @return - list of all users in group
     */
    List<PersonEntity> getMembers( Long id );

    /**
     search for Groups with specific members

     @param personEntity - owner to search

     @return - list of all groups which have personEntity as owner
     */
    List<GroupEntity> getByOwner( PersonEntity personEntity );

    /**
     search for Group by it`s ID

     @param id - ID of group

     @return - group with this ID
     */
    GroupEntity getById( Long id );

    /**
     get all groups

     @return all groups on server
     */
    List<GroupEntity> getAll();

    /**
     edit group

     @param groupEntity - edited group we need to save

     @return - edited group
     */
    GroupEntity editGroup( GroupEntity groupEntity );
}
