package com.chat_app.repository;

import com.chat_app.entity.Role;
import com.chat_app.type.RoleName;
import com.chat_app.valueobjects.RoleId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, RoleId> {

    Role findByName(RoleName name);

    @Override
    Optional<Role> findById(RoleId roleId);
}
