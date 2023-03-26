package com.blog.me.blogging.services;

import com.blog.me.blogging.payloads.CommentDTO;

public interface CommentService {

    CommentDTO newComment(CommentDTO commentDTO, Long userId, Integer postId);

    void deleteComment(Integer commentId);
}
