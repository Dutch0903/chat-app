package com.chat_app.domain.entity;

import com.chat_app.domain.type.ChatParticipantRole;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatParticipant {
    private ChatId chatId;
    private ParticipantId participantId;
    private ChatParticipantRole chatRole;
}
