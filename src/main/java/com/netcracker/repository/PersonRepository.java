package com.netcracker.repository;

import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Persons
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
