package com.netcracker.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface MessageService{

    /**
     Returns reactive stream of all events with specified group's messages

     @param id group id

     @return reactive stream of updates
     */
    SseEmitter getMessagesEmbitterByGroupId( Long id );

    /**
     add message to db

     @param groupId  - id of group
     @param writerId - id of person
     @param text     - text of message

     @throws IllegalArgumentException - if user or group not contains in db
     */
    void addMessage( Long groupId , Long writerId , String text ) throws IllegalArgumentException;

    /**
     Remove message from id

     @param writerId  - id of person
     @param messageId - id of message

     @throws IllegalArgumentException - if user or group not contains in db
     @throws IllegalStateException    - if user doesn't own this message
     */
    void deleteMessage( Long writerId , Long messageId ) throws IllegalArgumentException;
}
