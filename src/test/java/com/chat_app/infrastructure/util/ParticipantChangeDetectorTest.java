package com.chat_app.infrastructure.util;

import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.chat_app.testdata.ChatParticipantDataBuilder.aChatParticipantData;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ParticipantChangeDetectorTest {
    @InjectMocks
    private ChatParticipantChangeDetector chatParticipantChangeDetector;

    @Test
    public void detectChanges_WithNoExistingChatParticipantsAndNoNewParticipants_ReturnsNoDeletionsAndNoAdditions() {
        List<ParticipantData> existingParticipants = new ArrayList<>();
        List<ParticipantData> newParticipants = new ArrayList<>();

        ChangeResult<ParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertFalse(result.hasDeletions());
        assertTrue(result.getDeletions().isEmpty());

        assertFalse(result.hasAdditions());
        assertTrue(result.getAdditions().isEmpty());
    }

    @Test
    public void detectChanges_WithNoExistingChatParticipants_ReturnsNoDeletions() {
        UUID chatId = UUID.randomUUID();
        List<ParticipantData> existingParticipants = new ArrayList<>();
        List<ParticipantData> newParticipants = Arrays.asList(aChatParticipantData().withChatId(chatId).build(), aChatParticipantData().withChatId(chatId).build());

        ChangeResult<ParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertFalse(result.hasDeletions());
        assertTrue(result.getDeletions().isEmpty());
    }

    @Test
    public void detectChanges_WithExistingChatParticipantsAndNoNewParticipants_ReturnsAllExistingAsDeletions() {
        UUID chatId = UUID.randomUUID();
        ParticipantData existingParticipantData1 = aChatParticipantData().withChatId(chatId).build();
        ParticipantData existingParticipantData2 = aChatParticipantData().withChatId(chatId).build();

        List<ParticipantData> existingParticipants = Arrays.asList(existingParticipantData1, existingParticipantData2);

        List<ParticipantData> newParticipants = new ArrayList<>();

        ChangeResult<ParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertTrue(result.hasDeletions());
        List<ParticipantData> deletedParticipants = result.getDeletions();
        assertFalse(deletedParticipants.isEmpty());

        assertTrue(deletedParticipants.contains(existingParticipantData1));
        assertTrue(deletedParticipants.contains(existingParticipantData2));
    }

    @Test
    public void detectChanged_WithNoExistingChatParticipantsButWithNewParticipants_ReturnsAllNewAdditions() {
        UUID chatId = UUID.randomUUID();
        ParticipantData newParticipantData1 = aChatParticipantData().build();
        ParticipantData newParticipantData2 = aChatParticipantData().build();

        List<ParticipantData> existingParticipants = new ArrayList<>();
        List<ParticipantData> newParticipants = Arrays.asList(newParticipantData1, newParticipantData2);

        ChangeResult<ParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertTrue(result.hasAdditions());
        List<ParticipantData> addedParticipants = result.getAdditions();

        assertFalse(addedParticipants.isEmpty());

        assertTrue(addedParticipants.contains(newParticipantData1));
        assertTrue(addedParticipants.contains(newParticipantData2));
    }
}
