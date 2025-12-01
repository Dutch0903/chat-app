package com.chat_app.entity;

import com.chat_app.entity.ref.RoleRef;
import com.chat_app.valueobjects.UserId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table("users")
public class User implements Persistable<UserId> {
    @Id
    private UserId id;
    private String username;
    private String email;
    @MappedCollection(idColumn = "user_id", keyColumn = "role_id")
    private Set<RoleRef> roles;
    private String password;

    @Transient
    private boolean isNew = false;

    public User() {
    }

    public User(String username, String email, String password) {
        this.id = UserId.create();
        this.username = username;
        this.email = email;
        this.roles = new HashSet<>();
        this.password = password;

        this.isNew = true;
    }

    public boolean isNew() {
        return isNew;
    }

    public void assignRole(Role role) {
        roles.add(createRoleRef(role));
    }

    private RoleRef createRoleRef(Role role) {
        return new RoleRef(role.getId());
    }
}
