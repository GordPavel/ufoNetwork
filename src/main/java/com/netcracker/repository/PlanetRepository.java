package com.netcracker.repository;

import com.netcracker.DAO.PlanetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Planets
 */
public interface PlanetRepository extends JpaRepository<PlanetEntity, Long> {
}
