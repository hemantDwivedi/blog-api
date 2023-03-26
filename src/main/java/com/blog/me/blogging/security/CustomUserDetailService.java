package com.blog.me.blogging.security;

import com.blog.me.blogging.exceptions.ResourceNotFoundException;
import com.blog.me.blogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        return userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("user not found", username)
        );
    }
}
