package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImplementation implements GroupService{

    @Autowired private GroupRepository groupRepository;

    @Override
    public GroupEntity addGroup( GroupEntity groupEntity ){
        return groupRepository.saveAndFlush( groupEntity );
    }

    @Override
    public void delete( Long id ){
        groupRepository.deleteById( id );
    }

    @Override
    public List<GroupEntity> getBySearchParams( String name , String ownerName ){
        return groupRepository.getBySearchParams( name , ownerName );
    }

    @Override
    public List<PersonEntity> getMembers( Long id ){
        return groupRepository.getOne( id ).getUsers();
    }

    @Override
    public GroupEntity getById( Long id ){
        return groupRepository.getOne( id );
    }

    @Override
    public List<GroupEntity> getAll(){
        return groupRepository.findAll();
    }

    @Override
    public GroupEntity editGroup( GroupEntity groupEntity ){
        return groupRepository.saveAndFlush( groupEntity );
    }

    @Override
    public List<GroupEntity> getByOwner( PersonEntity personEntity ){
        return groupRepository.getByOwner( personEntity );
    }
}
