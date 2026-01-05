package com.chat_app.infrastructure.util;

import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.infrastructure.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ChatParticipantChangeDetector {

    private ChatParticipantDataSource chatParticipantDataSource;

    public ChangeResult<ChatParticipantData> detectChanges(UUID chatId, List<ChatParticipantData> participants) {
        List<ChatParticipantData> existingParticipants = this.chatParticipantDataSource.findAllByChatId(chatId);

        List<ChatParticipantData> newParticipants = new ArrayList<>(participants);
        newParticipants.removeAll(existingParticipants);

        List<ChatParticipantData> deletedParticipants = new ArrayList<>(existingParticipants);
        deletedParticipants.removeAll(newParticipants);

        return new ChangeResult<>(newParticipants, deletedParticipants);
    }
}
