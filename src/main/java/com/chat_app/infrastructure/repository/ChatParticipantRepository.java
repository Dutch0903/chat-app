package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.ChatParticipant;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.infrastructure.mapper.ChatParticipantMapper;
import com.chat_app.infrastructure.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import com.chat_app.infrastructure.util.ChangeResult;
import com.chat_app.infrastructure.util.ChatParticipantChangeDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ChatParticipantRepository {
    @Autowired
    private ChatParticipantDataSource chatParticipantDataSource;

    @Autowired
    private ChatParticipantMapper chatParticipantMapper;

    @Autowired
    private ChatParticipantChangeDetector chatParticipantChangeDetector;

    public List<ChatParticipant> getAllChatParticipants(ChatId chatId) {
        return chatParticipantDataSource.findAllByChatId(chatId.value())
                .stream()
                .map(chatParticipantData -> chatParticipantMapper.toEntity(chatParticipantData))
                .toList();
    }

    public void saveAll(List<ChatParticipant> chatParticipants) {
        List<ChatParticipantData> chatParticipantDataList = chatParticipants.stream().map(chatParticipant -> chatParticipantMapper.toData(chatParticipant)).toList();
        UUID chatId = chatParticipantDataList.stream().map(ChatParticipantData::getChatId).distinct().findFirst().orElse(null);
        if (chatId == null) {
            return;
        }

        List<ChatParticipantData> existingParticipants = chatParticipantDataSource.findAllByChatId(chatId);

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(chatParticipantDataList, existingParticipants);

        if (result.hasAdditions()) {
            chatParticipantDataSource.saveAll(
                    result.getAdditions()
                            .stream()
                            .map(chatParticipantData -> chatParticipantData.setId(UUID.randomUUID()).setIsNew(true))
                            .toList()
            );
        }

        if (result.hasDeletions()) {
            chatParticipantDataSource.deleteAll(result.getDeletions());
        }

        // Use a lookup map for existing participants to avoid repeated streaming
        java.util.Map<String, ChatParticipantData> existingMap = existingParticipants.stream()
                .collect(java.util.stream.Collectors.toMap(
                        p -> p.getParticipantId().toString(),
                        p -> p
                ));

        List<ChatParticipantData> updates = chatParticipantDataList.stream()
                .filter(newData -> {
                    ChatParticipantData existing = existingMap.get(newData.getParticipantId().toString());
                    return existing != null && !existing.getRole().equals(newData.getRole());
                })
                .peek(newData -> {
                    ChatParticipantData existing = existingMap.get(newData.getParticipantId().toString());
                    if (existing != null) {
                        newData.setId(existing.getId());
                    }
                })
                .toList();

        if (!updates.isEmpty()) {
            chatParticipantDataSource.saveAll(updates);
        }
    }
}
