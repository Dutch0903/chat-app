package com.chat_app.controller;

import com.chat_app.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(
            Message message,
            SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Message received: " + message);

        return message;
    }
}
