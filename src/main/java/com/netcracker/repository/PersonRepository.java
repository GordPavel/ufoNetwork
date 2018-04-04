package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 Repository interface for Persons
 */
@Repository
public interface PersonRepository
        extends JpaRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity>{

    @Query( "select pe.groups from PersonEntity pe where pe.id=:id " )
    List<GroupEntity> getGroups(
            @Param( "id" )
                    Long id );

    @Query( "select pe from PersonEntity pe where pe.login=:login AND pe.pass=:password " )
    PersonEntity login(
            @Param( "login" )
                    String login ,
            @Param( "password" )
                    String password );

    @Query( "select pe from PersonEntity pe where pe.login=:login" )
    PersonEntity getByLogin(
            @Param( "login" )
                    String login );
}
