package com.chat_app.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private final UUID id;

    private String password;

    private final String username;
    private final String email;
    private final Set<GrantedAuthority> authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetailsImpl that = (UserDetailsImpl) o;

        return Objects.equals(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
