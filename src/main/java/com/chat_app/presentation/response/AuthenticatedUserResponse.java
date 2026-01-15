package com.chat_app.presentation.response;

import com.chat_app.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Schema(name = "AuthenticatedUser")
public record AuthenticatedUserResponse(
        UUID id,
        String username,
        String email,
        List<String> authorities
) {
    public static AuthenticatedUserResponse fromUserDetails(UserDetailsImpl userDetails) {
        return new AuthenticatedUserResponse(
                userDetails.getId().value(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}
