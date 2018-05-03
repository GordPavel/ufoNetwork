package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.GroupLazyFields;
import com.netcracker.DAO.GroupMediaEntity;
import com.netcracker.controllers.forms.GroupCreateForm;
import com.netcracker.repository.GroupRepository;
import com.netcracker.service.GroupService;
import com.netcracker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImplementation implements GroupService{

    @Autowired private GroupRepository groupRepository;
    @Autowired private PersonService personService;

    @Override
    public Optional<GroupEntity> findById( Long id , GroupLazyFields... fields ){
        return groupRepository.findById( id ).map( group -> {
            List<GroupLazyFields> lazyFields = Arrays.asList( fields );
            if( lazyFields.contains( GroupLazyFields.MEDIA ) )
                group.setMedia( groupRepository.getMediaById( group.getId() ).get() );
            if( lazyFields.contains( GroupLazyFields.USERS ) )
                group.setUsers( groupRepository.getUsersById( group.getId() ) );
            if( lazyFields.contains( GroupLazyFields.MESSAGES ) )
                group.setMessages( groupRepository.getMessagesById( group.getId() ) );
            return group;
        } );
    }

    @Override
    public List<GroupEntity> listAll( GroupLazyFields... fields ){
        List<GroupLazyFields> lazyFields = Arrays.asList( fields );
        return groupRepository.findAll().parallelStream().peek( group -> {
            if( lazyFields.contains( GroupLazyFields.MEDIA ) )
                group.setMedia( groupRepository.getMediaById( group.getId() ).get() );
            if( lazyFields.contains( GroupLazyFields.USERS ) )
                group.setUsers( groupRepository.getUsersById( group.getId() ) );
            if( lazyFields.contains( GroupLazyFields.MESSAGES ) )
                group.setMessages( groupRepository.getMessagesById( group.getId() ) );
        } ).collect( Collectors.toList() );
    }

    @Override
    public Long addGroup( GroupCreateForm groupCreateForm, Long userId ) throws IOException{
        GroupEntity groupEntity = new GroupEntity( groupCreateForm.getName() , personService.findById( userId ).get() );
        if( groupCreateForm.getImage() != null ){
            GroupMediaEntity media = new GroupMediaEntity();
            media.setImage( groupCreateForm.getImage().getBytes() );
            media.setGroup( groupEntity );
            groupEntity.setMedia( media );
        }
        return groupRepository.saveAndFlush( groupEntity ).getId();
    }

    @Override
    public List<GroupEntity> getBySearchParams( String name , String ownerName ){
        return groupRepository.getBySearchParams( name , ownerName );
    }
}
