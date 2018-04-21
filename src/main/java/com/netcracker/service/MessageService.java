package com.netcracker.service;

public interface MessageService{

    /**
     add message to db

     @param groupId  - id of group
     @param writerId - id of person
     @param text     - text of message

     @return - added message
     */
    Long addMessage( Long groupId , Long writerId , String text );

}
