package com.chat_app.repository;

import com.chat_app.entity.Chat;
import com.chat_app.repository.jdbc.ChatDataSource;
import com.chat_app.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.repository.jdbc.data.ChatData;
import com.chat_app.repository.jdbc.data.ChatParticipantData;
import com.chat_app.valueobjects.ChatParticipantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepository {

    @Autowired
    private ChatDataSource chatDataSource;

    @Autowired
    private ChatParticipantDataSource chatParticipantDataSource;

    public List<Chat> getAllChats(ChatParticipantId chatParticipantId) {
        List<ChatParticipantData> chats = chatParticipantDataSource.findByParticipantId(chatParticipantId.value());

        return chatDataSource.findByIdIn(chats.stream().map(ChatParticipantData::getChatId).toList())
                .stream()
                .map(ChatData::toEntity)
                .toList();
    }
}
