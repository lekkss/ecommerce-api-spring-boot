package com.example.ecommerceapi.model;

public enum UserRole {
    USER("USER"),
    VENDOR("VENDOR"),
    ADMIN("ADMIN");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
