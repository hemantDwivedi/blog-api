package com.blog.me.blogging.services.Impl;

import com.blog.me.blogging.config.APIConstants;
import com.blog.me.blogging.config.ModelMap;
import com.blog.me.blogging.entity.Role;
import com.blog.me.blogging.entity.User;
import com.blog.me.blogging.exceptions.ResourceNotFoundException;
import com.blog.me.blogging.payloads.UserDto;
import com.blog.me.blogging.repository.RoleRepository;
import com.blog.me.blogging.repository.UserRepository;
import com.blog.me.blogging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMap modelMap;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = modelMap.modelMapper().map(userDto, User.class);
        // Encoded the user normal password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepository.findById(APIConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        return this.modelMap.modelMapper().map(this.userRepository.save(user),UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return this.toUserDto(this.userRepository.save(this.toUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!", id));
        updateUser.setUsername(userDto.getUsername());
        updateUser.setEmail(userDto.getEmail());
        updateUser.setPassword(userDto.getPassword());
        updateUser.setAbout(userDto.getAbout());


        return this.toUserDto(this.userRepository.save(updateUser));
    }

    @Override
    public UserDto getUserById(Long id) {
        return this.toUserDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!", id)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(this::toUserDto
                        ).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!", id));

        userRepository.delete(user);
    }

    private User toUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setUsername(userDto.getUsername());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setRole(userDto.getRole());
        return (User) this.modelMap.modelMapper().map(userDto, User.class);
    }

    private UserDto toUserDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setUsername(user.getUsername());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setRole(user.getRole());

        return (UserDto) this.modelMap.modelMapper().map(user, UserDto.class);
    }
}
