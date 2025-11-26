package com.chat_app.dto;

import com.chat_app.type.UserRole;

import java.util.Set;

public record RegistrationData (String username, String email, Set<UserRole> roles, String password) {
}
