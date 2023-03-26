package com.blog.me.blogging.payloads;

import com.blog.me.blogging.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Integer postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageName;
    private Date postDate;
    private CategoryDTO category;
    private UserDto user;

    private Set<CommentDTO> comments = new HashSet<>();
}
