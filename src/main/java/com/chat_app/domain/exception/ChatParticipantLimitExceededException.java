package com.chat_app.domain.exception;

public class ChatParticipantLimitExceededException extends RuntimeException {
    public ChatParticipantLimitExceededException() {
        super("Chat participant limit exceeded");
    }
}
