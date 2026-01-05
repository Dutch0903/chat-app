package com.chat_app.exception;

public class ChatAlreadyExistsException extends RuntimeException {
    public ChatAlreadyExistsException() {
        super("Chat already exists");
    }
}
