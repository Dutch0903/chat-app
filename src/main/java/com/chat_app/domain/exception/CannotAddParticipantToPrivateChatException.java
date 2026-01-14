package com.chat_app.domain.exception;

public class CannotAddParticipantToPrivateChatException extends RuntimeException {
    public CannotAddParticipantToPrivateChatException() {
        super("Cannot add a new participant to a direct chat");
    }
}
