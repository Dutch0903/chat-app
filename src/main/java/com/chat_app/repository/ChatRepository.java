package com.chat_app.repository;

import com.chat_app.entity.Chat;
import com.chat_app.repository.jdbc.ChatDataSource;
import com.chat_app.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.repository.jdbc.data.ChatData;
import com.chat_app.repository.jdbc.data.ChatParticipantData;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChatRepository {

    @Autowired
    private ChatDataSource chatDataSource;

    @Autowired
    private ChatParticipantDataSource chatParticipantDataSource;

    public List<Chat> getAllChats(ParticipantId participantId) {
        List<ChatParticipantData> chatParticipantDataList = chatParticipantDataSource.findAllByParticipantId(participantId.value());

        List<UUID> chatIdList = chatParticipantDataList.stream().map(ChatParticipantData::getChatId).toList();

        List<Chat> chats = new ArrayList<>();
        chatDataSource.findAllById(chatIdList).forEach(chatData -> chats.add(chatData.toEntity()));

        return chats;
    }

    public Chat getChatById(ChatId chatId) {
        return chatDataSource.findById(chatId.value()).map(ChatData::toEntity).orElse(null);
    }

    public Chat findChatByIdAndParticipantId(ChatId chatId, ParticipantId participantId) {
        boolean isParticipant = chatParticipantDataSource.existsByChatIdAndParticipantId(chatId.value(), participantId.value());
        if (!isParticipant) {
            return null;
        }

        Optional<ChatData> result = chatDataSource.findById(chatId.value());

        return result.map(ChatData::toEntity).orElse(null);
    }

    public boolean existsDirectChatBetweenParticipants(ParticipantId participantA, ParticipantId participantB)
    {
        return chatDataSource.existsDirectChat(participantA.value(), participantB.value());
    }

    public void insert(Chat chat) {
        chatDataSource.save(ChatData.fromEntity(chat, true));
    }

    public void update(Chat chat) {
        chatDataSource.save(ChatData.fromEntity(chat));
    }
}

