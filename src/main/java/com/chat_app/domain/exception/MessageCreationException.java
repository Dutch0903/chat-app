package com.chat_app.domain.exception;

public class MessageCreationException extends RuntimeException {
    public MessageCreationException(String message, Throwable cause) {
        super(message, cause);
    }
    public MessageCreationException(String message) {
        super(message);
    }
}
