package com.blog.me.blogging.services;

import com.blog.me.blogging.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long id);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void deleteUserById(Long id);
}
