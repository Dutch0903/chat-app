package com.chat_app.repository.jdbc;

import com.chat_app.repository.jdbc.data.ChatParticipantData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ChatParticipantDataSource extends CrudRepository<ChatParticipantData, UUID> {
    public List<ChatParticipantData> findByChatId(UUID chatId);

    public List<ChatParticipantData> findByParticipantId(UUID chatParticipantId);
}
