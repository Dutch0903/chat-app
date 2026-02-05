package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.Message;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.MessageId;
import com.chat_app.domain.valueobjects.SenderId;
import com.chat_app.infrastructure.repository.jdbc.data.MessageData;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toEntity(MessageData messageData) {
        return new Message(
                MessageId.from(messageData.getId()),
                ChatId.from(messageData.getChatId()),
                SenderId.from(messageData.getSenderId()),
                messageData.getContent(),
                messageData.getCreatedAt()
        );
    }

    public MessageData toData(Message message, boolean isNew) {
        return new MessageData(
                message.getId().value(),
                message.getChatId().value(),
                message.getSenderId().value(),
                message.getContent(),
                message.getCreatedAt(),
                isNew
        );
    }
}
