package com.netcracker.repository;

import com.netcracker.DAO.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Categories
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{ }
