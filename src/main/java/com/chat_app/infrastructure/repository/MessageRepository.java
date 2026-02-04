package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Message;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.infrastructure.mapper.MessageMapper;
import com.chat_app.infrastructure.repository.jdbc.MessageDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private MessageDataSource messageDataSource;

    private MessageMapper messageMapper;

    public MessageRepository(MessageDataSource messageDataSource, MessageMapper messageMapper) {
        this.messageDataSource = messageDataSource;
        this.messageMapper = messageMapper;
    }

    public List<Message> getAll(ChatId chatId) {
        List<MessageData> messageDataList = messageDataSource.findAllByChatId(chatId.value());

        return messageDataList.stream()
                .map(messageMapper::toEntity)
                .toList();
    }

    public Message getLatest(ChatId chatId) {
        MessageData latestMessage = messageDataSource.findLatestByChatId(chatId.value());

        return messageMapper.toEntity(latestMessage);
    }
}
