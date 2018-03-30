package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for Groups
 */
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{

    /**
     * query to select from db by name+owner name
     */
    @Query("select ge from GroupEntity ge where " +
            "((:name is null) or (?1 is not null and ge.name like ?1)) and " +
            "((:ownerName is null) or (?2 is not null and ge.owner like ?2))")
    List<GroupEntity> getBySearchParams(@Param("name")String name,
                                            @Param("ownerName")String ownerName);

    /**
     * query to get all members of group by ID
     */
    @Query("select ge.members from GroupEntity ge where ge.id=:id ")
    List<PersonEntity> getMembers(@Param("id")Long id);

    /**
     * query to select groups, where this person=owner
     */
    @Query("select ge from GroupEntity ge where ge.owner_group=:owner")
    List<GroupEntity> getByOwner(@Param("owner")PersonEntity owner);
}


