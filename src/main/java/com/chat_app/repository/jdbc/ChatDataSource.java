package com.chat_app.repository.jdbc;

import com.chat_app.repository.jdbc.data.ChatData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ChatDataSource extends CrudRepository<ChatData, UUID> {
    List<ChatData> findByIdIn(List<UUID> chatIds);
}
