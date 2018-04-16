package com.netcracker.impl;

import com.netcracker.DAO.MessageEntity;
import com.netcracker.repository.MessageRepository;
import com.netcracker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImplementation implements MessageService{

    @Autowired private MessageRepository messageRepository;

    @Override
    public MessageEntity addMessage( MessageEntity messageEntity ){

        return messageRepository.saveAndFlush( messageEntity );
    }

    @Override
    public void delete( Long id ){
        messageRepository.deleteById( id );
    }
}
