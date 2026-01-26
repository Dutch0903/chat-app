package com.chat_app.presentation.response;

import com.chat_app.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Schema(name = "AuthenticatedUser")
public record AuthenticatedUserResponse(
        @NotNull UUID id,
        @NotNull String username,
        @NotNull String email,
        @NotNull List<String> authorities
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
