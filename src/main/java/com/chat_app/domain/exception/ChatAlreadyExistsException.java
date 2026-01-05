package com.chat_app.domain.exception;

public class ChatAlreadyExistsException extends RuntimeException {
    public ChatAlreadyExistsException() {
        super("Chat already exists");
    }
}
