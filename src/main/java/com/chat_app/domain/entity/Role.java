package com.chat_app.domain.entity;

import com.chat_app.domain.valueobjects.RoleId;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("roles")
public class Role {
    @Id
    private final RoleId id;
    private final String name;

    public Role(RoleId id, String name) {
        this.id = id;
        this.name = name;
    }
}
