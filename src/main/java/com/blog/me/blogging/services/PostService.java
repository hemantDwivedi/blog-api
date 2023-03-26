package com.blog.me.blogging.services;

import com.blog.me.blogging.entity.Post;
import com.blog.me.blogging.payloads.PostDTO;
import com.blog.me.blogging.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO newPost(PostDTO postDTO, Long userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO, Integer id);
    void deletePost(Integer id);
    PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortType);
    PostDTO getPostById(Integer id);

    List<PostDTO> getPostsByCategory(Integer categoryId);
//    PostResponse getPostsByCategory(Integer categoryId,Integer pageSize, Integer pageNumber, String sortType);
    List<PostDTO> getPostsByUser(Long userId);
    List<PostDTO> searchPostsByKeyword(String keyword);
}
