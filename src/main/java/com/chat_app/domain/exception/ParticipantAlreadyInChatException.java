package com.chat_app.domain.exception;

public class ParticipantAlreadyInChatException extends RuntimeException {
    public ParticipantAlreadyInChatException() {
        super("Participant is already in chat");
    }
}
