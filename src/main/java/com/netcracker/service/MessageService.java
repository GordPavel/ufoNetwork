package com.netcracker.service;

import com.netcracker.DAO.GroupEntity;
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

    /**
     search for messages by group

     @param groupEntity - group to search

     @return - list of all mesages, posted oin group
     */
    List<MessageEntity> getMessagesByGroup( GroupEntity groupEntity );
}
