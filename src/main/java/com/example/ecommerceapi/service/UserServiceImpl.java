package com.example.ecommerceapi.service;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerceapi.Dto.UserDto;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.model.UserRole;
import com.example.ecommerceapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {

        // if (userRepository.existsByEmail(userDto.getEmail())) {
        // return new ResponseEntity<>("Username is already exist!",
        // HttpStatus.BAD_REQUEST);
        // }

        // Set default values if needed
        userDto.setUserId(UUID.randomUUID());
        userDto.setRole(UserRole.USER);
        userDto.setCreatedAt(new Date());
        userDto.setUpdatedAt(new Date());

        // userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Hash the password
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        System.out.println(hashedPassword + "------HASHED-------");
        userDto.setPassword(hashedPassword);

        // Map UserDto to User entity
        User user = modelMapper.map(userDto, User.class);

        // Additional logic if needed (e.g., validation, setting user properties)

        // Save the user entity
        User savedUser = userRepository.save(user);

        // Map the saved User entity back to UserDto excluding the password field
        // modelMapper.typeMap(User.class, UserDto.class).addMappings(mapping ->
        // mapping.skip(UserDto::setPassword));
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        return savedUserDto;
    }

}
