package com.chat_app.application.dto;

import com.chat_app.domain.type.RoleName;

import java.util.Set;

public record RegistrationData (String username, String email, Set<RoleName> roleNames, String password) {
}
