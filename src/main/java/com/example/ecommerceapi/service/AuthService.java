package com.example.ecommerceapi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerceapi.Dto.ReqRes;
import com.example.ecommerceapi.Dto.UserDto;
import com.example.ecommerceapi.config.JWTUtils;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.model.UserRole;
import com.example.ecommerceapi.repository.UserRepository;
import com.example.ecommerceapi.response.ResponseHandler;

import io.micrometer.common.util.StringUtils;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<?> signup(UserDto userDto) {
        // Validate userDto fields
        if (StringUtils.isBlank(userDto.getFirstname()) ||
                StringUtils.isBlank(userDto.getLastname()) ||
                StringUtils.isBlank(userDto.getEmail()) ||
                StringUtils.isBlank(userDto.getPassword())) {
            return ResponseHandler.generateResponse("All fields are required",
                    HttpStatus.BAD_REQUEST, null);
        }

        // if user exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseHandler.generateResponse("Email already in use",
                    HttpStatus.BAD_REQUEST, null);
        }

        // Set default values if needed
        userDto.setUserId(UUID.randomUUID());
        userDto.setRole(UserRole.USER);
        userDto.setCreatedAt(new Date());
        userDto.setUpdatedAt(new Date());

        // userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // Hash the password
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
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

        return ResponseHandler.generateResponse("User Successfully created",
                HttpStatus.OK, savedUserDto);
    }

    public ReqRes signin(ReqRes signinRequest) {
        ReqRes resp = new ReqRes();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow();

            System.out.println("USER IS: " + user);

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setMessage("User signed in successfully");
            resp.setStatusCode(200);
            resp.setExpirationTime("24Hr");
        } catch (

        Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes resp = new ReqRes();
        String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User users = userRepository.findByEmail(email).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshTokenRequest.getToken());
            resp.setMessage("Successfully refreshed token");
            resp.setStatusCode(200);
            resp.setExpirationTime("24Hr");
        }
        resp.setStatusCode(500);
        return resp;
    }
}
