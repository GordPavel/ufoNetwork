package com.netcracker.repository;

import com.netcracker.DAO.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Messages
 */
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
}
