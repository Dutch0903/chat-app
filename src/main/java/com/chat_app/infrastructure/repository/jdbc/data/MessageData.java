package com.chat_app.infrastructure.repository.jdbc.data;

import com.chat_app.domain.entity.Message;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.MessageId;
import com.chat_app.domain.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Table("chat_messages")
public class MessageData {
    @Id
    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private String content;

    private OffsetDateTime createdAt;

    public static MessageData fromEntity(Message chatMessage) {
        return new MessageData(
                chatMessage.getId().value(),
                chatMessage.getChatId().value(),
                chatMessage.getSenderId().value(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }

    public Message toEntity() {
        return new Message(
                new MessageId(this.id),
                new ChatId(this.chatId),
                new ParticipantId(this.senderId),
                this.content,
                this.createdAt
        );
    }
}
