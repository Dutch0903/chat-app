package com.chat_app.entity.ref;

import com.chat_app.valueobjects.RoleId;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("user_roles")
public class RoleRef {
    RoleId roleId;

    public RoleRef(RoleId roleId) {
        this.roleId = roleId;
    }
}
