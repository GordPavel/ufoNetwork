package com.netcracker.service;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.GroupLazyFields;
import com.netcracker.controllers.forms.GroupCreateForm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GroupService{

    /**
     add Group to db@param groupEntity - group to add
     */
    Long addGroup( GroupCreateForm groupCreateForm, Long userId ) throws IOException;

    /**
     search for Groups with specific owner name

     @param ownerName - *+ownerName+* to search
     @param name      - *+name+* to search

     @return - list of all groups suitable for search parameters
     */
    List<GroupEntity> getBySearchParams( String name , String ownerName );

    /**
     Find one group and load specified lazy properties

     @param id     - id of group
     @param fields - specified group's properties to load

     @return Optional of group if id is valid and otherwise empty Optional
     */
    Optional<GroupEntity> findById( Long id , GroupLazyFields... fields );

    /**
     List groups and load specified lazy properties

     @param fields - specified group's properties to load

     @return list of groups
     */
    List<GroupEntity> listAll( GroupLazyFields... fields );
}
