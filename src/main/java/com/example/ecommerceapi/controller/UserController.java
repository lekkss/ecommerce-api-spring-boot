package com.example.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerceapi.Dto.UserDto;
import com.example.ecommerceapi.service.UserServiceImpl;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    // @PostMapping("/register")
    // public ResponseEntity<?> addUser(@RequestBody UserDto userDTO) {

    // ResponseEntity<?> addedUserDTO = userService.addUser(userDTO);
    // return (addedUserDTO);
    // }
}
