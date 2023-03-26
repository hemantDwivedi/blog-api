package com.blog.me.blogging.services.Impl;

import com.blog.me.blogging.config.ModelMap;
import com.blog.me.blogging.entity.Category;
import com.blog.me.blogging.entity.Post;
import com.blog.me.blogging.entity.User;
import com.blog.me.blogging.exceptions.ResourceNotFoundException;
import com.blog.me.blogging.payloads.PostDTO;
import com.blog.me.blogging.payloads.PostResponse;
import com.blog.me.blogging.repository.CategoryRepository;
import com.blog.me.blogging.repository.PostRepository;
import com.blog.me.blogging.repository.UserRepository;
import com.blog.me.blogging.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMap modelMap;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDTO newPost(PostDTO postDTO, Long userId, Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Category not found:", userId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found:",categoryId));
        Post post = this.modelMap.modelMapper().map(postDTO, Post.class);
        post.setUser(user);
        post.setCategory(category);
        post.setImageName("default.png");
        post.setPostDate(new Date());
        this.postRepository.save(post);
        return this.modelMap.modelMapper().map(post,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", id)
        );

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return modelMap.modelMapper().map(post, PostDTO.class);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", id)
        );

        this.postRepository.delete(post);

    }

    @Override
    public PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortType) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortType));
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent();

        List<PostDTO> postDTOList = postList
                .stream()
                .map(
                        post -> this.modelMap.modelMapper().map(post, PostDTO.class)
                ).collect(Collectors.toList());


        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElement(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());


        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", id)
        );
        return modelMap.modelMapper().map(post, PostDTO.class);
    }

//    @Override
//    public PostResponse getPostsByCategory(Integer categoryId, Integer pageSize, Integer pageNumber, String sortType) {
//        Category category = categoryRepository.findById(categoryId).orElseThrow(
//                () -> new ResourceNotFoundException("Category not found:", categoryId)
//        );
//
//        Pageable pageable = PageRequest.of(pageSize, pageNumber, Sort.by(sortType));
//        List<Post> categoryPage = postRepository.findByCategory(category);
//        Page<Post> postPage = postRepository.
//        List<Post> postList = postPage.getContent();
//
//        List<PostDTO> postDTOList = postList
//                .stream()
//                .map(
//                        post -> this.modelMap.modelMapper().map(post, PostDTO.class)
//                ).collect(Collectors.toList());
//
//
//        PostResponse postResponse = new PostResponse();
//
//        postResponse.setContent(postDTOList);
//        postResponse.setPageNumber(postPage.getNumber());
//        postResponse.setPageSize(postPage.getSize());
//        postResponse.setTotalElement(postPage.getTotalElements());
//        postResponse.setTotalPages(postPage.getTotalPages());
//        postResponse.setLastPage(postPage.isLast());
//
//
//        return postResponse;
//    }


    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", categoryId)
        );

        List<Post> postList = postRepository.findByCategory(category);

        return postList
                .stream()
                .map(
                        post -> modelMap.modelMapper().map(post, PostDTO.class)
                ).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", userId)
        );

        List<Post> postList = postRepository.findByUser(user);
        return postList
                .stream()
                .map(
                        post -> modelMap.modelMapper().map(post, PostDTO.class)
                ).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPostsByKeyword(String keyword) {
        List<Post> postByTitle = postRepository.findPostByTitle(keyword);

        return postByTitle
                .stream()
                .map(
                        post -> modelMap.modelMapper().map(post, PostDTO.class)
                ).collect(Collectors.toList());
    }
}
