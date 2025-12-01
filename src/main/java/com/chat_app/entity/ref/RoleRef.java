package com.chat_app.entity.ref;

import com.chat_app.valueobjects.RoleId;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public class RoleRef {
    RoleId roleId;

    public RoleRef(RoleId roleId) {
        this.roleId = roleId;
    }
}
