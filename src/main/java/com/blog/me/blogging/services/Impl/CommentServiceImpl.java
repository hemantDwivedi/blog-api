package com.blog.me.blogging.services.Impl;

import com.blog.me.blogging.config.ModelMap;
import com.blog.me.blogging.entity.Comment;
import com.blog.me.blogging.entity.Post;
import com.blog.me.blogging.entity.User;
import com.blog.me.blogging.exceptions.ResourceNotFoundException;
import com.blog.me.blogging.payloads.CommentDTO;
import com.blog.me.blogging.repository.CommentRepository;
import com.blog.me.blogging.repository.PostRepository;
import com.blog.me.blogging.repository.UserRepository;
import com.blog.me.blogging.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMap modelMap;

    @Override
    public CommentDTO newComment(CommentDTO commentDTO, Long userId, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", postId)
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", userId)
        );
        Comment comment = modelMap.modelMapper().map(commentDTO, Comment.class);

        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return modelMap.modelMapper().map(comment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.delete(commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found:", commentId)
        ));
    }
}
