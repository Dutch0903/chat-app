package com.chat_app.infrastructure.security;

import com.chat_app.domain.entity.Role;
import com.chat_app.domain.entity.User;
import com.chat_app.infrastructure.repository.RoleRepository;
import com.chat_app.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Set<GrantedAuthority> authorities = getRoleNames(user).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return UserDetailsImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .id(user.getId())
                .build();
    }

    private List<String> getRoleNames(User user) {
        List<String> roleNames = new ArrayList<>();

        user.getRoles().stream().forEach(roleRef -> {
            Optional<Role> result = roleRepository.findById(roleRef.getRoleId());

            if (result.isEmpty()) {
                return;
            }

            Role role = result.get();

            roleNames.add(role.getName());
        });

        return roleNames;
    }
}
