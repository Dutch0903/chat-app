package com.chat_app.exception;

import com.chat_app.entity.Chat;

public class CanNotAddParticipantToDirectChatException extends RuntimeException {
    public CanNotAddParticipantToDirectChatException() {
        super("Can not add a new participant to a direct chat");
    }
}
