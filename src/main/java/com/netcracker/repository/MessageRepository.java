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

    @Query( "select me from MessageEntity me where me.toGroup = :group" )
    List<MessageEntity> getByGroup(
            @Param( "group" )
                    GroupEntity group );
}
