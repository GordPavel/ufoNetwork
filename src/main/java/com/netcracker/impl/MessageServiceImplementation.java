package com.netcracker.impl;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.MessageEntity;
import com.netcracker.repository.MessageRepository;
import com.netcracker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<MessageEntity> getMessagesByGroup( GroupEntity groupEntity ){

        return messageRepository.getMessagesByGroup( groupEntity );
    }

    @Override
    public MessageEntity getMessageById(Long id){

        return messageRepository.getOne(id);
    }

}
