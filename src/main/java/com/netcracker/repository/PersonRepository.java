package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for Persons
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("select pe.groups from PersonEntity pe where pe.id=:id ")
    List<GroupEntity> getGroups (long Id);
}
