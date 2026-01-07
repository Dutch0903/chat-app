package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.mapper.ChatMapper;
import com.chat_app.infrastructure.mapper.ChatParticipantMapper;
import com.chat_app.infrastructure.repository.jdbc.ChatDataSource;
import com.chat_app.infrastructure.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class ChatRepository {
    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatParticipantMapper chatParticipantMapper;

    @Autowired
    private ChatDataSource chatDataSource;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private ChatParticipantDataSource chatParticipantDataSource;

    public List<Chat> getAllChats(ParticipantId participantId) {
        List<ChatParticipantData> chatParticipantDataList = chatParticipantDataSource.findAllByParticipantId(participantId.value());

        List<UUID> chatIds = chatParticipantDataList.stream().map(ChatParticipantData::getChatId).toList();
        List<ChatParticipantData> allParticipants = chatParticipantDataSource.findAllByChatIdIn(chatIds);

        return StreamSupport.stream(chatDataSource.findAllById(chatIds).spliterator(), false)
                .map(chatData -> chatMapper.toEntity(
                        chatData,
                        allParticipants.stream()
                                .filter(chatParticipantData -> chatParticipantData.getChatId().equals(chatData.getId()))
                                .map(chatParticipantData -> chatParticipantMapper.toEntity(chatParticipantData))
                                .toList()
                ))
                .toList();
    }

    public Chat findChatByIdAndParticipantId(ChatId chatId, ParticipantId participantId) {
        boolean isParticipant = chatParticipantDataSource.existsByChatIdAndParticipantId(chatId.value(), participantId.value());
        if (!isParticipant) {
            return null;
        }

        Optional<ChatData> result = chatDataSource.findById(chatId.value());

        if (result.isEmpty()) {
            return null;
        }

        ChatData chatData = result.get();

        return chatMapper.toEntity(chatData, chatParticipantRepository.getAllChatParticipants(ChatId.from(chatData.getId())));
    }

    public boolean existsDirectChatBetweenParticipants(ParticipantId participantA, ParticipantId participantB) {
        return chatDataSource.existsDirectChat(participantA.value(), participantB.value());
    }

    public void insert(Chat chat) {
        ChatData chatData = chatMapper.toData(chat, true);

        chatDataSource.save(chatData);

        chatParticipantRepository.saveAll(chat.getParticipants());
    }
}

