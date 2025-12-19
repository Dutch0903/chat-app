package com.chat_app.entity;

import com.chat_app.type.ChatParticipantRole;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatParticipant {
    private ChatId chatId;
    private ParticipantId participantId;
    private ChatParticipantRole chatRole;
}
