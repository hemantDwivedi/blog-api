package com.blog.me.blogging.controllers;

import com.blog.me.blogging.payloads.APIResponse;
import com.blog.me.blogging.payloads.CategoryDTO;
import com.blog.me.blogging.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return this.categoryService.getAllCategory();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId){
        return new ResponseEntity<CategoryDTO>(categoryService.getById(categoryId), HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDTO, id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCategoryById(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(new APIResponse("Category deleted with id: " +id),HttpStatus.OK);
    }
}
