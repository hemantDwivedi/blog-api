package com.blog.me.blogging.controllers;

import com.blog.me.blogging.config.ModelMap;
import com.blog.me.blogging.entity.User;
import com.blog.me.blogging.exceptions.APIException;
import com.blog.me.blogging.payloads.JwtAuthRequest;
import com.blog.me.blogging.payloads.JwtAuthenticationResponse;
import com.blog.me.blogging.payloads.UserDto;
import com.blog.me.blogging.security.JwtTokenHelper;
import com.blog.me.blogging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMap modelMap;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){

        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String generateToken = jwtTokenHelper.generateToken(userDetails);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();

        response.setToken(generateToken);

        response.setUser(this.modelMap.modelMapper().map((User) userDetails, UserDto.class));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e){
            System.out.println("Invalid Details!");
            throw new APIException("Invalid username or password");
        }
    }

    // register user endpoint
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        return new ResponseEntity<>(this.userService.registerUser(userDto), HttpStatus.CREATED);
    }
}
