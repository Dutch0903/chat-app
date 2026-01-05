package com.chat_app.infrastructure.repository.jdbc;

import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import com.chat_app.infrastructure.repository.jdbc.data.id.ChatParticipantId;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ChatParticipantDataSource extends CrudRepository<ChatParticipantData, ChatParticipantId> {
    @Query("SELECT * FROM chat_participants WHERE participant_id = :participantId")
    List<ChatParticipantData> findAllByParticipantId(UUID participantId);

    @Query("SELECT * FROM chat_participants WHERE chat_id = :chatId")
    List<ChatParticipantData> findAllByChatId(UUID chatId);

    @Query("SELECT EXISTS(SELECT 1 FROM chat_participants WHERE chat_id = :chatId AND participant_id = :participantId)")
    boolean existsByChatIdAndParticipantId(UUID chatId, UUID participantId);
}
