package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 Repository interface for Groups
 */
@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{

    /**
     query to select from db by name + owner name
     */

//    todo правильнее все же использовать не имя пользователя, а логин
    @Query( "select ge from GroupEntity ge where ge.name like :name and ge.owner.name like :ownerName" )
    List<GroupEntity> getBySearchParams(
            @Param( "name" )
                    String name ,
            @Param( "ownerName" )
                    String ownerName );

    /**
     query to select groups, where this person=owner
     */
    @Query( "select ge from GroupEntity ge where ge.owner=:owner" )
    List<GroupEntity> getByOwner(
            @Param( "owner" )
                    PersonEntity owner );
}


