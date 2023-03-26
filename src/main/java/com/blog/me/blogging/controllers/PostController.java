package com.blog.me.blogging.controllers;

import com.blog.me.blogging.config.APIConstants;
import com.blog.me.blogging.payloads.APIResponse;
import com.blog.me.blogging.payloads.PostDTO;
import com.blog.me.blogging.payloads.PostResponse;
import com.blog.me.blogging.services.FileService;
import com.blog.me.blogging.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // Create a new post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long userId, @PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.newPost(postDTO,userId,categoryId), HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageSize", defaultValue = APIConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = APIConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "sortType", defaultValue = APIConstants.SORT_TYPE, required = false)  String sortType
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageSize, pageNumber, sortType), HttpStatus.FOUND);
    }

    // Get Posts by User ID
    @GetMapping("/user/{userId}/posts")
    public List<PostDTO> getPostsByUser(@PathVariable Long userId){
        return postService.getPostsByUser(userId);
    }

    // Get Posts by Category ID
    @GetMapping("/category/{categoryId}/posts")
    public List<PostDTO> getPostByCategory(@PathVariable Integer categoryId){
        return postService.getPostsByCategory(categoryId);
    }

    // Get post by post ID
    @GetMapping("/post/{postID}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postID){
        return new ResponseEntity<>(postService.getPostById(postID), HttpStatus.FOUND);
    }

    // Get Post by Keyword
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> getPostByKeyword(@PathVariable("keyword") String keyword){
        return new ResponseEntity<>(postService.searchPostsByKeyword(keyword), HttpStatus.FOUND);
    }

    // Update a post by ID
    @PutMapping("/posts/{postID}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postID){
        return new ResponseEntity<>(postService.updatePost(postDTO, postID), HttpStatus.OK);
    }

    // Delete a post by ID
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<APIResponse> deletePostById(@PathVariable Integer id){
        postService.deletePost(id);

        return new ResponseEntity<>(new APIResponse("post deleted with id: " + id),HttpStatus.OK);
    }

    // Post Image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("file") MultipartFile file, @PathVariable Integer postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, file);
        postDTO.setImageName(fileName);
        PostDTO updatePost = this.postService.updatePost(postDTO, postId);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    // Get image
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(
            @PathVariable String imageName, HttpServletResponse response
    ) throws  IOException{
        InputStream inputStream = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
