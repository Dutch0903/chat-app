package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.mapper.ParticipantMapper;
import com.chat_app.infrastructure.repository.jdbc.ParticipantDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import com.chat_app.infrastructure.util.ChangeResult;
import com.chat_app.infrastructure.util.ChatParticipantChangeDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ParticipantRepository {
    @Autowired
    private ParticipantDataSource participantDataSource;

    @Autowired
    private ParticipantMapper participantMapper;

    @Autowired
    private ChatParticipantChangeDetector chatParticipantChangeDetector;

    public boolean existsByChatIdAndParticipantId(ChatId chatId, ParticipantId participantId) {
        return participantDataSource.existsByChatIdAndParticipantId(chatId.value(), participantId.value());
    }

    public List<Participant> getAllChatParticipants(ChatId chatId) {
        return participantDataSource.findAllByChatId(chatId.value())
                .stream()
                .map(chatParticipantData -> participantMapper.toEntity(chatParticipantData))
                .toList();
    }

    public List<Participant> getAllChatParticipants(List<UUID> chatIds) {
        return participantDataSource.findAllByChatIdIn(chatIds)
                .stream()
                .map(participantMapper::toEntity)
                .toList();
    }

    public List<ParticipantData> getAllParticipatingChats(ParticipantId participantId) {
        return participantDataSource.findAllByParticipantId(participantId.value());
    }

    public void saveAll(List<Participant> participants) {
        List<ParticipantData> participantDataList = participants.stream().map(chatParticipant -> participantMapper.toData(chatParticipant)).toList();
        UUID chatId = participantDataList.stream().map(ParticipantData::getChatId).distinct().findFirst().orElse(null);
        if (chatId == null) {
            return;
        }

        List<ParticipantData> existingParticipants = participantDataSource.findAllByChatId(chatId);

        ChangeResult<ParticipantData> result = chatParticipantChangeDetector.detectChanges(participantDataList, existingParticipants);

        if (result.hasAdditions()) {
            participantDataSource.saveAll(
                    result.getAdditions()
                            .stream()
                            .map(chatParticipantData -> chatParticipantData.setId(UUID.randomUUID()).setIsNew(true))
                            .toList()
            );
        }

        if (result.hasDeletions()) {
            participantDataSource.deleteAll(result.getDeletions());
        }

        // Use a lookup map for existing participants to avoid repeated streaming
        java.util.Map<String, ParticipantData> existingMap = existingParticipants.stream()
                .collect(java.util.stream.Collectors.toMap(
                        p -> p.getParticipantId().toString(),
                        p -> p
                ));

        List<ParticipantData> updates = participantDataList.stream()
                .filter(newData -> {
                    ParticipantData existing = existingMap.get(newData.getParticipantId().toString());
                    return existing != null && !existing.getRole().equals(newData.getRole());
                })
                .peek(newData -> {
                    ParticipantData existing = existingMap.get(newData.getParticipantId().toString());
                    if (existing != null) {
                        newData.setId(existing.getId());
                    }
                })
                .toList();

        if (!updates.isEmpty()) {
            participantDataSource.saveAll(updates);
        }
    }
}
