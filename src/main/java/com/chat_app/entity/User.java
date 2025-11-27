package com.chat_app.entity;

import com.chat_app.type.UserRole;
import com.chat_app.valueobjects.UserId;
import lombok.Getter;

import java.util.Set;

@Getter
public class User {
    private UserId id;
    private String username;
    private String email;
    private Set<UserRole> roles;
    private String password;

    public User(String username, String email, Set<UserRole> roles, String password) {
        this.id = UserId.create();
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public User(UserId id, String username, String email, Set<UserRole> roles, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }
}
