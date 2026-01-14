package com.chat_app.infrastructure.repository.jdbc;

import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ParticipantDataSource extends CrudRepository<ParticipantData, UUID> {
    @Query("SELECT * FROM chat_participants WHERE participant_id = :participantId")
    List<ParticipantData> findAllByParticipantId(UUID participantId);

    @Query("SELECT * FROM chat_participants WHERE chat_id = :chatId")
    List<ParticipantData> findAllByChatId(UUID chatId);

    List<ParticipantData> findAllByChatIdIn(List<UUID> chatIds);

    @Query("SELECT EXISTS(SELECT 1 FROM chat_participants WHERE chat_id = :chatId AND participant_id = :participantId)")
    boolean existsByChatIdAndParticipantId(UUID chatId, UUID participantId);
}
