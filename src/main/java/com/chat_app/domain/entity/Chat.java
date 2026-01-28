package com.chat_app.domain.entity;

import com.chat_app.domain.exception.CannotAddParticipantToPrivateChatException;
import com.chat_app.domain.exception.CannotRemoveParticipantFromPrivateChatException;
import com.chat_app.domain.exception.ChatParticipantLimitExceededException;
import com.chat_app.domain.exception.ParticipantAlreadyInChatException;
import com.chat_app.domain.enums.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Chat {
    private ChatId id;
    private ChatType type;
    private String name;

    private List<Participant> participants;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private static final Integer MAX_PARTICIPANTS_NUMBER = 10;

    public Chat(ChatId id, ChatType type, String name, List<Participant> participants) {
        this.id = id;
        this.type = type;
        this.participants = participants;
        this.name = name;

        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public void addParticipant(Participant participant) {
        if (type.equals(ChatType.PRIVATE)) {
            throw new CannotAddParticipantToPrivateChatException();
        }

        if (participants.contains(participant)) {
            throw new ParticipantAlreadyInChatException();
        }

        if (participants.size() + 1 >= MAX_PARTICIPANTS_NUMBER) {
            throw new ChatParticipantLimitExceededException();
        }

        participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        if (type.equals(ChatType.PRIVATE)) {
            throw new CannotRemoveParticipantFromPrivateChatException();
        }

        participants.remove(participant);
    }
}
