package com.chat_app.request;

import com.chat_app.dto.RegistrationData;
import com.chat_app.type.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@NotNull
public record RegisterRequest(
        @NotBlank(message = "Username is required")
        @Size(max=100, message = "Username cannot be longer then 100 characters")
        String username,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {

    public RegistrationData getRegistrationData() {
        return new RegistrationData(
                username,
                email,
                Set.of(UserRole.USER),
                password
        );
    }
}
