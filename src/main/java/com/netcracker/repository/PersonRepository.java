package com.netcracker.repository;


import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 Repository interface for Persons
 */
@org.springframework.stereotype.Repository
public interface PersonRepository
        extends org.springframework.data.repository.Repository<PersonEntity, Long>,
                JpaRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity>{

    @Query( "select pe from PersonEntity pe where pe.login=:login" )
    Optional<PersonEntity> getByLogin(
            @Param( "login" )
                    String login );
}
