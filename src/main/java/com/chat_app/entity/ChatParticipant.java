package com.chat_app.entity;

import com.chat_app.type.ChatParticipantRole;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ChatParticipantId;

public class ChatParticipant {
    private ChatId chatId;
    private ChatParticipantId chatParticipantId;
    private ChatParticipantRole chatRole;
}
