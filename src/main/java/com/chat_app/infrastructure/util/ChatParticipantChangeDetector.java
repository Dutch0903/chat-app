package com.chat_app.infrastructure.util;

import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatParticipantChangeDetector {
    public ChangeResult<ParticipantData> detectChanges(List<ParticipantData> participants, List<ParticipantData> existingParticipants) {
        List<ParticipantData> newParticipants = new ArrayList<>(participants);
        newParticipants.removeAll(existingParticipants);

        List<ParticipantData> deletedParticipants = new ArrayList<>(existingParticipants);
        deletedParticipants.removeAll(newParticipants);

        return new ChangeResult<>(newParticipants, deletedParticipants);
    }
}
