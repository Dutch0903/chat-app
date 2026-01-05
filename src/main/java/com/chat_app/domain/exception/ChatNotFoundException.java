package com.chat_app.domain.exception;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {super("Chat not found");}
}
