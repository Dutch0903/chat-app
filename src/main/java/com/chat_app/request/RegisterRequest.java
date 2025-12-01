package com.chat_app.request;

import com.chat_app.dto.RegistrationData;
import com.chat_app.type.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NotNull
public record RegisterRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username cannot be longer then 100 characters")
        String username,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        Set<String> roles,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {

    public RegistrationData getRegistrationData() {
        Set<RoleName> roleNames = roles != null ? roles.stream()
                .filter(RoleName::isValid)
                .map(RoleName::valueOf)
                .collect(Collectors.toSet()) : new HashSet<>();

        return new RegistrationData(
                username,
                email,
                roleNames,
                password
        );
    }
}
