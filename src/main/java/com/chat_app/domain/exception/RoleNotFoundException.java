package com.chat_app.domain.exception;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super("Role not found");
    }
}
