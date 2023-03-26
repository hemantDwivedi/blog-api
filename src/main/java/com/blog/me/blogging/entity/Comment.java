package com.blog.me.blogging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_ID")
    private Post post;

}
