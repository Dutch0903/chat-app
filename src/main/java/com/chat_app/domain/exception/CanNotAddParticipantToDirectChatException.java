package com.chat_app.domain.exception;

public class CanNotAddParticipantToDirectChatException extends RuntimeException {
    public CanNotAddParticipantToDirectChatException() {
        super("Can not add a new participant to a direct chat");
    }
}
