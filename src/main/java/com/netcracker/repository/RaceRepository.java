package com.netcracker.repository;

import com.netcracker.DAO.RaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for Races
 */
public interface RaceRepository extends JpaRepository<RaceEntity,Long> {

    /**
     * search for race with name...
     */
    @Query("select re from RaceEntity re where re.name like ?1")
    RaceEntity getByName(@Param("name") String name);
}
