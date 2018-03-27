package com.netcracker.service;

import com.netcracker.DAO.CategoryEntity;

import java.util.List;

public interface CategoryService {

    /**
     * add Category to db
     * @param categoryEntity - category to add
     * @return - added category
     */
    CategoryEntity addCategory(CategoryEntity categoryEntity);

    /**
     * delete Category from db by it`s ID
     * @param id - ID of Category
     */
    void delete(Long id);

    /**
     * get Category by it`s ID
     * @param id - ID of Category
     * @return - Category with this ID
     */
    CategoryEntity getById(Long id);

    /**
     * get list of all Categories
     * @return all Categories in db
     */
    List<CategoryEntity> getAll();

    /**
     * edit Category
     * @param categoryEntity - edited Category to save in db
     * @return - edited Category
     */
    CategoryEntity editCategory(CategoryEntity categoryEntity);

}
