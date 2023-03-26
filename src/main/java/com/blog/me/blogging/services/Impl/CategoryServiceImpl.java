package com.blog.me.blogging.services.Impl;

import com.blog.me.blogging.config.ModelMap;
import com.blog.me.blogging.entity.Category;
import com.blog.me.blogging.exceptions.ResourceNotFoundException;
import com.blog.me.blogging.payloads.CategoryDTO;
import com.blog.me.blogging.repository.CategoryRepository;
import com.blog.me.blogging.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMap modelMap;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMap.modelMapper().map(categoryDTO, Category.class);
        Category newCategory = this.categoryRepository.save(category);
        return this.modelMap.modelMapper().map(newCategory, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories
                .stream()
                .map((category) -> this.modelMap.modelMapper().map(category,CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getById(Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found:", id));
        return this.modelMap.modelMapper().map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found:", id));
        category.setCategoryName(categoryDTO.getCategoryName());
        Category updateCategory = this.categoryRepository.save(category);
        return this.modelMap.modelMapper().map(updateCategory,CategoryDTO.class);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found:", id));
        this.categoryRepository.delete(category);
    }
}
