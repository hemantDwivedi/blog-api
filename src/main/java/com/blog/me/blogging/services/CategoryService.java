package com.blog.me.blogging.services;

import com.blog.me.blogging.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    // POST-MAPPING: CREATE CATEGORY
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    // GET-MAPPING: GET ALL CATEGORIES
    List<CategoryDTO> getAllCategory();
    // GET-MAPPING: GET CATEGORY BY ID
    CategoryDTO getById(Integer id);
    // PUT-MAPPING: UPDATE EXISTING CATEGORY OR CREATE ONE
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id);
    //DELETE-MAPPING: DELETE CATEGORY BY AN ID
    void deleteCategoryById(Integer id);
}
