package com.netcracker.repository;

import com.netcracker.DAO.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 Repository interface for Messages
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{ }
