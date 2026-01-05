package com.chat_app.domain.exception;

public class UsernameOrEmailAlreadyInUseException extends Exception {
    public UsernameOrEmailAlreadyInUseException() {
        super(
                "Username or Email is already in use"
        );
    }
}
