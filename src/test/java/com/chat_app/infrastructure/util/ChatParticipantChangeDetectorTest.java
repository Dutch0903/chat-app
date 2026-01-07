package com.chat_app.infrastructure.util;

import com.chat_app.infrastructure.repository.jdbc.ChatParticipantDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.chat_app.testdata.ChatParticipantDataBuilder.aChatParticipantData;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatParticipantChangeDetectorTest {
    @InjectMocks
    private ChatParticipantChangeDetector chatParticipantChangeDetector;

    @Test
    public void detectChanges_WithNoExistingChatParticipantsAndNoNewParticipants_ReturnsNoDeletionsAndNoAdditions() {
        List<ChatParticipantData> existingParticipants = new ArrayList<>();
        List<ChatParticipantData> newParticipants = new ArrayList<>();

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertFalse(result.hasDeletions());
        assertTrue(result.getDeletions().isEmpty());

        assertFalse(result.hasAdditions());
        assertTrue(result.getAdditions().isEmpty());
    }

    @Test
    public void detectChanges_WithNoExistingChatParticipants_ReturnsNoDeletions() {
        UUID chatId = UUID.randomUUID();
        List<ChatParticipantData> existingParticipants = new ArrayList<>();
        List<ChatParticipantData> newParticipants = Arrays.asList(aChatParticipantData().withChatId(chatId).build(), aChatParticipantData().withChatId(chatId).build());

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertFalse(result.hasDeletions());
        assertTrue(result.getDeletions().isEmpty());
    }

    @Test
    public void detectChanges_WithExistingChatParticipantsAndNoNewParticipants_ReturnsAllExistingAsDeletions() {
        UUID chatId = UUID.randomUUID();
        ChatParticipantData existingParticipantData1 = aChatParticipantData().withChatId(chatId).build();
        ChatParticipantData existingParticipantData2 = aChatParticipantData().withChatId(chatId).build();

        List<ChatParticipantData> existingParticipants = Arrays.asList(existingParticipantData1, existingParticipantData2);

        List<ChatParticipantData> newParticipants = new ArrayList<>();

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertTrue(result.hasDeletions());
        List<ChatParticipantData> deletedParticipants = result.getDeletions();
        assertFalse(deletedParticipants.isEmpty());

        assertTrue(deletedParticipants.contains(existingParticipantData1));
        assertTrue(deletedParticipants.contains(existingParticipantData2));
    }

    @Test
    public void detectChanged_WithNoExistingChatParticipantsButWithNewParticipants_ReturnsAllNewAdditions() {
        UUID chatId = UUID.randomUUID();
        ChatParticipantData newChatParticipantData1 = aChatParticipantData().build();
        ChatParticipantData newChatParticipantData2 = aChatParticipantData().build();

        List<ChatParticipantData> existingParticipants = new ArrayList<>();
        List<ChatParticipantData> newParticipants = Arrays.asList(newChatParticipantData1, newChatParticipantData2);

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(newParticipants, existingParticipants);

        assertTrue(result.hasAdditions());
        List<ChatParticipantData> addedParticipants = result.getAdditions();

        assertFalse(addedParticipants.isEmpty());

        assertTrue(addedParticipants.contains(newChatParticipantData1));
        assertTrue(addedParticipants.contains(newChatParticipantData2));
    }
}
