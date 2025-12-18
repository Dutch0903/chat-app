package com.chat_app.repository.jdbc.data;

import com.chat_app.entity.Chat;
import com.chat_app.type.ChatType;
import com.chat_app.valueobjects.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Table("chats")
public class ChatData {
    @Id
    private UUID id;
    private String type;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static ChatData fromEntity(Chat chat) {
        return new ChatData(
                chat.getId().value(),
                chat.getType().name(),
                chat.getName(),
                chat.getCreatedAt(),
                chat.getUpdatedAt()
        );
    }

    public Chat toEntity() {
        return new Chat(
                new ChatId(this.id),
                ChatType.valueOf(this.type),
                this.name,
                this.createdAt,
                this.updatedAt
        );
    }
}
