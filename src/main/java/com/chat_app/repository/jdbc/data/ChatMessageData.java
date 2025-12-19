package com.chat_app.repository.jdbc.data;

import com.chat_app.entity.ChatMessage;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ChatMessageId;
import com.chat_app.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Table("chat_messages")
public class ChatMessageData {
    @Id
    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private String content;

    private OffsetDateTime createdAt;

    public static ChatMessageData fromEntity(ChatMessage chatMessage) {
        return new ChatMessageData(
                chatMessage.getId().value(),
                chatMessage.getChatId().value(),
                chatMessage.getSenderId().value(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }

    public ChatMessage toEntity() {
        return new ChatMessage(
                new ChatMessageId(this.id),
                new ChatId(this.chatId),
                new ParticipantId(this.senderId),
                this.content,
                this.createdAt
        );
    }
}
