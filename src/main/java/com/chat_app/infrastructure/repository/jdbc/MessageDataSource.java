package com.chat_app.infrastructure.repository.jdbc;

import com.chat_app.infrastructure.repository.jdbc.data.MessageData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MessageDataSource extends CrudRepository<MessageData, UUID> {

    List<MessageData> findAllByChatId(UUID chatId);

    @Query("SELECT * FROM chat_messages WHERE chat_id = :chatId ORDER BY created_at DESC LIMIT 1")
    MessageData findLatestByChatId(UUID chatId);
}
