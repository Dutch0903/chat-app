package com.chat_app.exception;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {super("Chat not found");}
}
