package com.chat_app.repository;

import com.chat_app.entity.ChatParticipant;
import com.chat_app.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.repository.jdbc.data.ChatParticipantData;
import com.chat_app.valueobjects.ChatId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatParticipantRepository {
    @Autowired
    private ChatParticipantDataSource chatParticipantDataSource;

    public List<ChatParticipant> getAllChatParticipants(ChatId chatId) {
        return chatParticipantDataSource.findAllByChatId(chatId.value()).stream().map(ChatParticipantData::toEntity).toList();
    }

    public void insert(ChatParticipant chatParticipant) {
        chatParticipantDataSource.save(ChatParticipantData.from(chatParticipant, true));
    }
}
