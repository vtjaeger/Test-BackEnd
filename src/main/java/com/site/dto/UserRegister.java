package com.site.dto;

import com.site.enums.UserRole;

public record UserRegister(String name, String email, String password, String telephone, UserRole role) {
}
