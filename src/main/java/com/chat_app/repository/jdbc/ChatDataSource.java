package com.chat_app.repository.jdbc;

import com.chat_app.repository.jdbc.data.ChatData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface ChatDataSource extends CrudRepository<ChatData, UUID> {

    @Query("SELECT EXISTS ( SELECT 1 FROM chats c JOIN chat_participants cp ON c.id = cp.chat_id WHERE c.type = 'DIRECT' AND cp.participant_id IN (:participantIdA, :participantIdB) GROUP BY c.id HAVING COUNT(DISTINCT cp.participant_id) = 2)")
    boolean existsDirectChat(UUID participantIdA, UUID participantIdB);
}
