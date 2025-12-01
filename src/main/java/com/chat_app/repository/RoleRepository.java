package com.chat_app.repository;

import com.chat_app.entity.Role;
import com.chat_app.type.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {

    Role findByName(RoleName name);
}
