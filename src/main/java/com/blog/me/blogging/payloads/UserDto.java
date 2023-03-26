package com.blog.me.blogging.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    @NotEmpty(message = "username cannot be null")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 16,message = "password should be 8-16 characters")
    private String password;
    @NotEmpty(message = "role cannot be null")
    private String about;

    private Set<RoleDTO> roles = new HashSet<>();
}
