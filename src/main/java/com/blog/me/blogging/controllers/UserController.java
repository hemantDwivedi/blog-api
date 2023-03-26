package com.blog.me.blogging.controllers;

import com.blog.me.blogging.payloads.APIResponse;
import com.blog.me.blogging.payloads.UserDto;
import com.blog.me.blogging.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST-MAPPING: create user
    @PostMapping
    public ResponseEntity<UserDto> newUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
    }


    // GET-MAPPING: getting all users
    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    // GET-MAPPING: Get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.FOUND);
    }

    // PUT-MAPPING: BY ID
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateExistUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        return new ResponseEntity<>(userService.updateUser(userDto,userId), HttpStatus.ACCEPTED);
    }


    /* for testing ADMIN and NORMAL user access.
    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String hello(){
        return "hello world";
    }*/

    // DELETE-MAPPING: BY ID.
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>(new APIResponse("User Deleted with id: " + userId),HttpStatus.OK);
    }
}
