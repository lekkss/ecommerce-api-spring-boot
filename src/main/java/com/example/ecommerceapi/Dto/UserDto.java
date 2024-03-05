package com.example.ecommerceapi.Dto;

import java.util.Date;
import java.util.UUID;

import com.example.ecommerceapi.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private UUID userId;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private UserRole role;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

}
