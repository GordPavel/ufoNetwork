package com.netcracker.impl;

import com.netcracker.DAO.MessageEntity;
import com.netcracker.repository.MessageRepository;
import com.netcracker.service.GroupService;
import com.netcracker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService{

    @Autowired private MessageRepository messageRepository;
    @Autowired private GroupService groupService;

    @Override
    public MessageEntity addMessage( MessageEntity messageEntity ){

        return messageRepository.saveAndFlush( messageEntity );
    }

    @Override
    public void delete( Long id ){
        messageRepository.deleteById( id );
    }

    @Override
    public List<MessageEntity> getByGroup(Long id) {return messageRepository.getByGroup(groupService.getById(id).get());}
}
