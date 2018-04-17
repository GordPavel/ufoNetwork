package com.netcracker.service;

import com.netcracker.DAO.MessageEntity;

import java.util.List;

public interface MessageService{

    /**
     add message to db

     @param messageEntity - message to add

     @return - added message
     */
    MessageEntity addMessage( MessageEntity messageEntity );

    /**
     delete message by ID

     @param id - id of message to delete
     */
    void delete( Long id );

    List<MessageEntity> getByGroup( Long id);
}
