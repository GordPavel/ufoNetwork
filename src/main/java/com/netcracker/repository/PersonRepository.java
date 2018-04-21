package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.MessageEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.DAO.PersonMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 Repository interface for Persons
 */
@SuppressWarnings( "SpringDataRepositoryMethodReturnTypeInspection" )
@org.springframework.stereotype.Repository
public interface PersonRepository
        extends org.springframework.data.repository.Repository<PersonEntity, Long>,
                JpaRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity>{

    @Query( "select pe from PersonEntity pe where pe.login=:login" )
    Optional<PersonEntity> getByLogin(
            @Param( "login" )
                    String login );

    @Query( "select pe.media from PersonEntity pe where pe.id=:id" )
    Optional<PersonMediaEntity> getMediaById(
            @Param( "id" )
                    Long id );

    @Query( "select pe.groups from PersonEntity pe where pe.id=:id" )
    List<GroupEntity> getGroupsById(
            @Param( "id" )
                    Long id );

    @Query( "select pe.rulingGroups from PersonEntity pe where pe.id=:id" )
    List<GroupEntity> getRulingGroupsById(
            @Param( "id" )
                    Long id );

    @Query( "select pe.messages from PersonEntity pe where pe.id=:id" )
    List<MessageEntity> getMessagesById(
            @Param( "id" )
                    Long id );

}
