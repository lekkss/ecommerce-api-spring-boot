package com.example.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerceapi.model.User;

public interface UserRepository
                extends JpaRepository<User, Long> {

        boolean existsByEmail(String email);

}
