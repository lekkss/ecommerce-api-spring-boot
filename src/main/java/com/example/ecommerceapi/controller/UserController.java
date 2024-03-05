package com.example.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerceapi.Dto.UserDto;
import com.example.ecommerceapi.repository.UserRepository;
import com.example.ecommerceapi.service.UserServiceImpl;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        UserDto addedUserDTO = userService.addUser(userDTO);
        return ResponseEntity.ok(addedUserDTO);
    }
}
