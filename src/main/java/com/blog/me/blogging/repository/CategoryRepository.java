package com.blog.me.blogging.repository;

import com.blog.me.blogging.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
