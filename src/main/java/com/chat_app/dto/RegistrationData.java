package com.chat_app.dto;

import com.chat_app.type.RoleName;

import java.util.Set;

public record RegistrationData (String username, String email, Set<RoleName> roleNames, String password) {
}
