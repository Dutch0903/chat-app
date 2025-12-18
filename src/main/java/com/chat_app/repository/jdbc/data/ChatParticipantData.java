package com.chat_app.repository.jdbc.data;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Table("chat_participants")
public class ChatParticipantData {
    @Id
    private UUID id;
    private UUID chatId;
    private UUID participantId;
    private String role;
}
