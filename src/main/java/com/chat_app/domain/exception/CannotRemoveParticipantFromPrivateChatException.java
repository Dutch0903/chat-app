package com.chat_app.domain.exception;

public class CannotRemoveParticipantFromPrivateChatException extends RuntimeException {
    public CannotRemoveParticipantFromPrivateChatException() {
        super("Cannot remove participant from private chat");
    }
}
