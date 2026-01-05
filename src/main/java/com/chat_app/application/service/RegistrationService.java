package com.chat_app.application.service;

import com.chat_app.application.dto.RegistrationData;
import com.chat_app.domain.entity.Role;
import com.chat_app.domain.entity.User;
import com.chat_app.domain.exception.RoleNotFoundException;
import com.chat_app.domain.exception.UsernameOrEmailAlreadyInUseException;
import com.chat_app.infrastructure.repository.UserRepository;
import com.chat_app.domain.type.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(
            RegistrationData registrationData
    ) throws UsernameOrEmailAlreadyInUseException, RoleNotFoundException {
        if (userRepository.existsByUsernameOrEmail(registrationData.username(), registrationData.email())) {
            throw new UsernameOrEmailAlreadyInUseException();
        }

        Set<RoleName> roleNames = registrationData.roleNames();
        if (roleNames.isEmpty()) {
            roleNames.add(RoleName.USER);
        }

        Set<Role> roles = this.roleService.getRolesByName(roleNames);

        User user = new User(registrationData.username(), registrationData.email(), passwordEncoder.encode(registrationData.password()));

        roles.forEach(user::assignRole);

        userRepository.save(user);
    }
}
