package com.site.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("usuario");
    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
