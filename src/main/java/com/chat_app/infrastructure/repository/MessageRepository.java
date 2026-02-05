package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Message;
import com.chat_app.domain.exception.MessageCreationException;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.infrastructure.mapper.MessageMapper;
import com.chat_app.infrastructure.repository.jdbc.MessageDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final MessageDataSource messageDataSource;

    private final MessageMapper messageMapper;

    public void insert(Message message) {
        MessageData messageData = messageMapper.toData(message, true);

        try {
            messageDataSource.save(messageData);
        } catch (DataIntegrityViolationException e) {
            throw new MessageCreationException(
                    "Unable to create message with id " + message.getId().value() + ". The message may already exists or there was a data integrity issue.",
                    e
            );
        }
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
