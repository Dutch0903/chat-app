package com.chat_app.exception;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super("Role not found");
    }
}
