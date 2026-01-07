package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.ChatParticipant;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import org.springframework.stereotype.Component;

@Component
public class ChatParticipantMapper {

    public ChatParticipantData toData(ChatParticipant chatParticipant) {
        return new ChatParticipantData(
                null,
                chatParticipant.getChatId().value(),
                chatParticipant.getParticipantId().value(),
                chatParticipant.getChatRole()
        );
    }

    public ChatParticipant toEntity(ChatParticipantData chatParticipantData) {
        return new ChatParticipant(
            new ChatId(chatParticipantData.getChatId()),
            new ParticipantId(chatParticipantData.getParticipantId()),
            chatParticipantData.getRole()
        );
    }
}
