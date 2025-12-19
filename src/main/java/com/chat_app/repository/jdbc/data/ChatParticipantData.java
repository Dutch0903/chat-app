package com.chat_app.repository.jdbc.data;

import com.chat_app.entity.ChatParticipant;
import com.chat_app.type.ChatParticipantRole;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
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

    public ChatParticipant toEntity() {
        return new ChatParticipant(
                new ChatId(chatId),
                new ParticipantId(participantId),
                ChatParticipantRole.valueOf(role)
        );
    }
}
