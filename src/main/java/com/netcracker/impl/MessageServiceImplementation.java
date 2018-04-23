package com.netcracker.impl;

import com.netcracker.DAO.MessageEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.MessageRepository;
import com.netcracker.repository.PersonRepository;
import com.netcracker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageServiceImplementation implements MessageService{

    @Autowired private MessageRepository messageRepository;
    @Autowired private PersonRepository  personRepository;
    @Autowired private GroupRepository   groupRepository;
    @Autowired private EntityManager     entityManager;

    @SuppressWarnings( "MismatchedQueryAndUpdateOfCollection" ) private ConcurrentHashMap<Long, List<SseEmitter>>
            emittersByGroups = new ConcurrentHashMap<>();

    @Override
    public SseEmitter getMessagesEmbitterByGroupId( Long id ){
        SseEmitter sseEmitter = new SseEmitter( Duration.ofHours( 1 ).toMillis() );
        Optional.ofNullable( emittersByGroups.get( id ) ).orElseGet( () -> {
            List<SseEmitter> emitters = new ArrayList<>();
            emittersByGroups.put( id , emitters );
            return emitters;
        } ).add( sseEmitter );
        sseEmitter.onCompletion( () -> {
            System.err.println( "end" );
            emittersByGroups.get( id ).remove( sseEmitter );
        } );
        sseEmitter.onError( ( error ) -> {
            System.out.println( "error" );
            error.printStackTrace();
            emittersByGroups.get( id ).remove( sseEmitter );
        } );
        sseEmitter.onTimeout( () -> {
            System.err.println( "timeout" );
            emittersByGroups.get( id ).remove( sseEmitter );
        } );
        return sseEmitter;
    }

    @Override
    public void addMessage( Long groupId , Long writerId , String text ){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setWriter( personRepository.findById( writerId )
                                                 .orElseThrow( () -> new IllegalArgumentException(
                                                         "В базе нет пользователя с id " +
                                                         writerId ) ) );
        messageEntity.setToGroup( groupRepository.findById( groupId )
                                                 .orElseThrow( () -> new IllegalArgumentException(
                                                         "В базе нет группы с id " + writerId ) ) );
        messageEntity.setText( text );
        Long messageId = messageRepository.saveAndFlush( messageEntity ).getId();
//        Чтобы сразу можно было получить данные из бд, а не из кеша hibernate
        entityManager.clear();
        MessageEntity message =
                messageRepository.findById( messageId ).orElseThrow( IllegalStateException::new );
        Optional.ofNullable( emittersByGroups.get( groupId ) ).ifPresent( emitters -> {
            SseEmitter.SseEventBuilder data = SseEmitter.event()
                                                        .reconnectTime( 500L )
                                                        .name( "post" )
                                                        .data( new HashMap<String, String>(){{
                                                            put( "id" ,
                                                                 message.getId().toString() );
                                                            put( "text" , message.getText() );
                                                            put( "date" ,
                                                                 message.getDateOfSubmition()
                                                                        .format( DateTimeFormatter.ofPattern(
                                                                                "dd.MM.yyyy HH:mm" ) ) );
                                                            put( "writerId" ,
                                                                 message.getWriter()
                                                                        .getId()
                                                                        .toString() );
                                                            put( "writerName" ,
                                                                 message.getWriter().getName() );
                                                        }} , MediaType.APPLICATION_JSON_UTF8 );
            emitters.forEach( emitter -> {
                try{
                    emitter.send( data );
                }catch( IOException e ){
                    emitter.completeWithError( e );
                }
            } );
        } );
    }

    @Override
    public void deleteMessage( Long writerId , Long messageId ){
        MessageEntity message = messageRepository.findById( messageId )
                                                 .orElseThrow( () -> new IllegalArgumentException(
                                                         "В базе нет сообщения с id " +
                                                         messageId ) );
        if( !message.getWriter().getId().equals( writerId ) )
            throw new IllegalStateException( String.format(
                    "Пользователь %d не может удалить сообщение %d" ,
                    writerId ,
                    messageId ) );
        Long groupId = message.getToGroup().getId();
        messageRepository.delete( message );
        Optional.ofNullable( emittersByGroups.get( groupId ) ).ifPresent( emitters -> {
            SseEmitter.SseEventBuilder data = SseEmitter.event()
                                                        .reconnectTime( 500L )
                                                        .name( "delete" )
                                                        .data( new HashMap<String, String>(){{
                                                            put( "id" , messageId.toString() );
                                                        }} , MediaType.APPLICATION_JSON_UTF8 );
            emitters.forEach( emitter -> {
                try{
                    emitter.send( data );
                }catch( IOException e ){
                    emitter.completeWithError( e );
                }
            } );
        } );
    }
}
