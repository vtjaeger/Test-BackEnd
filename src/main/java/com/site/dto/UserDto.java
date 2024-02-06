package com.site.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull
        String name,
        @NotNull
        @Email
        String email,
        @NotNull
        String password,
        @NotNull
        String telephone) {
}
