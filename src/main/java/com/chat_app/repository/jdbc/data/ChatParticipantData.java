package com.chat_app.repository.jdbc.data;

import com.chat_app.entity.ChatParticipant;
import com.chat_app.repository.jdbc.data.id.ChatParticipantId;
import com.chat_app.type.ChatParticipantRole;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Table("chat_participants")
public class ChatParticipantData implements Persistable<ChatParticipantId> {
    @Id
    private ChatParticipantId id;

    private ChatParticipantRole role;

    @Transient
    private boolean isNew;

    public static ChatParticipantData from(ChatParticipant chatParticipant) {
        return new ChatParticipantData(
                new ChatParticipantId(
                        chatParticipant.getChatId().value(),
                        chatParticipant.getParticipantId().value()
                ),
                chatParticipant.getChatRole(),
                false
        );
    }

    public static ChatParticipantData from(ChatParticipant chatParticipant, boolean isNew) {
        ChatParticipantData data = ChatParticipantData.from(chatParticipant);

        data.isNew = isNew;

        return data;
    }

    public ChatParticipant toEntity() {
        return new ChatParticipant(
                new ChatId(id.participantId()),
                new ParticipantId(id.chatId()),
                role
        );
    }

    public UUID getChatId() {
        return id.chatId();
    }

    public UUID getParticipantId() {
        return id.participantId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
