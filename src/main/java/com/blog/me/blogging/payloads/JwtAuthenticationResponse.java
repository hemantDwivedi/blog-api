package com.blog.me.blogging.payloads;


import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;

    private UserDto user;
}
