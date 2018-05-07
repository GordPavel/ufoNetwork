package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.GroupMediaEntity;
import com.netcracker.DAO.MessageEntity;
import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 Repository interface for Groups
 */
@SuppressWarnings( "SpringDataRepositoryMethodReturnTypeInspection" )
@Repository
public interface GroupRepository
        extends JpaRepository<GroupEntity, Long>, JpaSpecificationExecutor<GroupEntity>{

    /**
     query to select from db by name + owner name
     */

    @Query( "select ge from GroupEntity ge where UPPER(ge.name) like UPPER(:name)" +
            " and UPPER(ge.owner.name) like UPPER(:ownerName)" )
    List<GroupEntity> getBySearchParams(
            @Param( "name" )
                    String name ,
            @Param( "ownerName" )
                    String ownerName );

    @Query( "select ge from GroupEntity ge where UPPER(ge.name) like UPPER(:name)" )
    List<GroupEntity> getByName(
            @Param( "name" )
                    String name );

    @Query( "select gr.media from GroupEntity gr where gr.id=:id" )
    Optional<GroupMediaEntity> getMediaById(
            @Param( "id" )
                    Long id );

    @Query( "select gr.users from GroupEntity gr where gr.id=:id" )
    List<PersonEntity> getUsersById(
            @Param( "id" )
                    Long id );

    @Query( "select gr.messages from GroupEntity gr where gr.id=:id" )
    List<MessageEntity> getMessagesById(
            @Param( "id" )
                    Long id );
}


