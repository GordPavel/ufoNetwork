package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Groups
 */
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{ }


