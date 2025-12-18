package com.chat_app.service;

import com.chat_app.entity.Chat;
import com.chat_app.repository.ChatRepository;
import com.chat_app.valueobjects.ChatParticipantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getAllChats(ChatParticipantId chatParticipantId) {
        return this.chatRepository.getAllChats(chatParticipantId);
    }
}
