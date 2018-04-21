package com.netcracker.impl;

import com.netcracker.DAO.MessageEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.MessageRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImplementation implements MessageService{

    @Autowired private MessageRepository messageRepository;
    @Autowired private PersonRepository  personRepository;
    @Autowired private GroupRepository   groupRepository;

    @Override
    public Long addMessage( Long groupId , Long writerId , String text )
            throws IllegalArgumentException{
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setWriter( personRepository.findById( writerId )
                                                 .orElseThrow( IllegalArgumentException::new ) );
        messageEntity.setToGroup( groupRepository.findById( groupId )
                                                 .orElseThrow( IllegalArgumentException::new ) );
        messageEntity.setText( text );
        return messageRepository.saveAndFlush( messageEntity ).getId();
    }
}
