package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 Repository interface for Messages
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{

    /**
     get all messages in group
     */
    @Query( "select ge from MessageEntity ge where ge.toGroup=:group" )
    List<MessageEntity> getMessagesByGroup(
            @Param( "group" )
                    GroupEntity groupEntity );
}
