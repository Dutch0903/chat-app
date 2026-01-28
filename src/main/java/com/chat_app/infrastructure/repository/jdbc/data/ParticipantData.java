package com.chat_app.infrastructure.repository.jdbc.data;

import com.chat_app.domain.enums.ParticipantRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("chat_participants")
public class ParticipantData implements Persistable<UUID> {

    @Id
    private UUID id;
    private UUID chatId;
    private UUID participantId;
    private ParticipantRole role;

    @Transient
    private boolean isNew;

    public ParticipantData(
            UUID id,
            UUID chatId,
            UUID participantId,
            ParticipantRole role
    ) {
        this.id = id;
        this.chatId = chatId;
        this.participantId = participantId;
        this.role = role;
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public ParticipantData setId(UUID id) {
        this.id = id;
        return this;
    }

    public ParticipantData setIsNew(boolean isNew) {
        this.isNew = isNew;
        return this;
    }
}
