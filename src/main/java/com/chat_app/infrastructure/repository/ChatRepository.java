package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.exception.ChatCreationException;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.mapper.ChatMapper;
import com.chat_app.infrastructure.repository.jdbc.ChatDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;
import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
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
    private ParticipantRepository participantRepository;

    public List<Chat> getAllChats(ParticipantId participantId) {
        List<ParticipantData> chatParticipants = participantRepository.getAllParticipatingChats(participantId);

        if (chatParticipants.isEmpty()) {
            return Collections.emptyList();
        }

        List<UUID> chatIds = chatParticipants.stream().map(ParticipantData::getChatId).toList();
        List<Participant> allParticipants = participantRepository.getAllChatParticipants(chatIds);

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
        if (!participantRepository.existsByChatIdAndParticipantId(chatId, participantId)) {
            return null;
        }

        Optional<ChatData> result = chatDataSource.findById(chatId.value());

        if (result.isEmpty()) {
            return null;
        }

        ChatData chatData = result.get();

        return chatMapper.toEntity(chatData, participantRepository.getAllChatParticipants(ChatId.from(chatData.getId())));
    }

    public boolean existsPrivateChatBetweenParticipants(ParticipantId participantA, ParticipantId participantB) {
        return chatDataSource.existsPrivateChat(participantA.value(), participantB.value());
    }

    public void insert(Chat chat) {
        ChatData chatData = chatMapper.toData(chat, true);

        try {
            chatDataSource.save(chatData);

            participantRepository.saveAll(chat.getParticipants());
        } catch (DataIntegrityViolationException e) {
            throw new ChatCreationException(
                    "Unable to create chat with id " + chat.getId().value() + ". The chat may already exist or there was a data integrity issue.",
                    e
            );
        }
    }
}

