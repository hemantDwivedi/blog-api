package com.blog.me.blogging.payloads;


import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}
