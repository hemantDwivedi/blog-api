package com.blog.me.blogging.repository;

import com.blog.me.blogging.entity.Category;
import com.blog.me.blogging.entity.Post;
import com.blog.me.blogging.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findPostByTitle(String title);
}
