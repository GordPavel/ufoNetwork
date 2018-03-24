package com.netcracker.repository;

import com.netcracker.DAO.RaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Races
 */
public interface RaceRepository extends JpaRepository<RaceEntity,Long> {
}
