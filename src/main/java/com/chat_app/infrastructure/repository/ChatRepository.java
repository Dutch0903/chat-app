package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.ChatParticipant;
import com.chat_app.domain.exception.ChatCreationException;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.mapper.ChatMapper;
import com.chat_app.infrastructure.repository.jdbc.ChatDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class ChatRepository {
    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatDataSource chatDataSource;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    public List<Chat> getAllChats(ParticipantId participantId) {
        List<ChatParticipantData> chatParticipants = chatParticipantRepository.getAllParticipatingChats(participantId);

        if (chatParticipants.isEmpty()) {
            return Collections.emptyList();
        }

        List<UUID> chatIds = chatParticipants.stream().map(ChatParticipantData::getChatId).toList();
        List<ChatParticipant> allParticipants = chatParticipantRepository.getAllChatParticipants(chatIds);

        return StreamSupport.stream(chatDataSource.findAllById(chatIds).spliterator(), false)
                .map(chatData -> chatMapper.toEntity(
                        chatData,
                        allParticipants.stream()
                                .filter(chatParticipant -> chatParticipant.getChatId().value().equals(chatData.getId()))
                                .toList()
                ))
                .toList();
    }

    public Chat findChatByIdAndParticipantId(ChatId chatId, ParticipantId participantId) {
        if (!chatParticipantRepository.existsByChatIdAndParticipantId(chatId, participantId)) {
            return null;
        }

        Optional<ChatData> result = chatDataSource.findById(chatId.value());

        if (result.isEmpty()) {
            return null;
        }

        ChatData chatData = result.get();

        return chatMapper.toEntity(chatData, chatParticipantRepository.getAllChatParticipants(ChatId.from(chatData.getId())));
    }

    public boolean existsPrivateChatBetweenParticipants(ParticipantId participantA, ParticipantId participantB) {
        return chatDataSource.existsPrivateChat(participantA.value(), participantB.value());
    }

    public void insert(Chat chat) {
        ChatData chatData = chatMapper.toData(chat, true);

        try {
            chatDataSource.save(chatData);

            chatParticipantRepository.saveAll(chat.getParticipants());
        } catch (DataIntegrityViolationException e) {
            throw new ChatCreationException(
                    "Unable to create chat with id " + chat.getId().value() + ". The chat may already exist or there was a data integrity issue.",
                    e
            );
        }
    }
}

