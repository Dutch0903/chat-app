package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Role;
import com.chat_app.domain.enums.RoleName;
import com.chat_app.domain.valueobjects.RoleId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, RoleId> {

    Role findByName(RoleName name);

    @Override
    Optional<Role> findById(RoleId roleId);
}
