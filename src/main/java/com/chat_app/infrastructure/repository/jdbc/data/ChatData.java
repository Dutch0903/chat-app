package com.chat_app.infrastructure.repository.jdbc.data;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.type.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
}
