package com.chat_app.infrastructure.repository.jdbc.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("chat_messages")
public class MessageData implements Persistable<UUID> {
    @Id
    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private String content;

    private OffsetDateTime createdAt;

    @Transient
    private boolean isNew;
}
