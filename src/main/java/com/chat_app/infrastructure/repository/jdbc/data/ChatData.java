package com.chat_app.infrastructure.repository.jdbc.data;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.type.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Table("chats")
public class ChatData implements Persistable<UUID> {
    @Id
    private UUID id;
    private String type;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @Transient
    private boolean isNew;

    public static ChatData fromEntity(Chat chat) {
        return new ChatData(
                chat.getId().value(),
                chat.getType().name(),
                chat.getName(),
                chat.getCreatedAt(),
                chat.getUpdatedAt(),
                false
        );
    }

    public static ChatData fromEntity(Chat chat, boolean isNew) {
        ChatData data = ChatData.fromEntity(chat);
        data.isNew = isNew;

        return data;
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
