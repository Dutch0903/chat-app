package com.chat_app.application.service;

import com.chat_app.domain.entity.Role;
import com.chat_app.domain.exception.RoleNotFoundException;
import com.chat_app.infrastructure.repository.RoleRepository;
import com.chat_app.domain.enums.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> getRolesByName(Set<RoleName> roleNames) throws RoleNotFoundException {
        return roleNames.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
    }
}
