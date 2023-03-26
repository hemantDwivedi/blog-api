package com.blog.me.blogging.controllers;

import com.blog.me.blogging.payloads.APIResponse;
import com.blog.me.blogging.payloads.CommentDTO;
import com.blog.me.blogging.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDTO> newComment(
            @Valid @RequestBody CommentDTO commentDTO, @PathVariable Integer postId,
            @PathVariable Long userId
    ){
        return new ResponseEntity<>(commentService.newComment(commentDTO,userId, postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new APIResponse("comment deleted with Id: " + commentId),HttpStatus.OK);
    }
}
