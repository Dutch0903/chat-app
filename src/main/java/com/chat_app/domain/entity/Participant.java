package com.chat_app.domain.entity;

import com.chat_app.domain.enums.ParticipantRole;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Participant {
    private ChatId chatId;
    private ParticipantId participantId;
    private ParticipantRole participantRole;
}
