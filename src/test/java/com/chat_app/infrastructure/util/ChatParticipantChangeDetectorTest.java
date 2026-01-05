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
import static com.chat_app.testdata.ChatParticipantIdBuilder.aChatParticipantId;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatParticipantChangeDetectorTest {
    @Mock
    private ChatParticipantDataSource chatParticipantDataSource;

    @InjectMocks
    private ChatParticipantChangeDetector chatParticipantChangeDetector;

    @Test
    public void detectChanges_WithNoExistingChatParticipantsAndNoNewParticipants_ReturnsNoDeletionsAndNoAdditions() {
        UUID chatId = UUID.randomUUID();
        List<ChatParticipantData> existingParticipants = new ArrayList<>();
        List<ChatParticipantData> newParticipants = new ArrayList<>();

        when(chatParticipantDataSource.findAllByChatId(chatId)).thenReturn(existingParticipants);

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(chatId, newParticipants);

        assertFalse(result.hasDeletions());
        assertTrue(result.getDeletions().isEmpty());

        assertFalse(result.hasAdditions());
        assertTrue(result.getAdditions().isEmpty());
    }

    @Test
    public void detectChanges_WithNoExistingChatParticipants_ReturnsNoDeletions() {
        UUID chatId = UUID.randomUUID();
        List<ChatParticipantData> existingParticipants = new ArrayList<>();
        List<ChatParticipantData> newParticipants = Arrays.asList(aChatParticipantData().withChatParticipantId(aChatParticipantId().withChatId(chatId).build()).build(), aChatParticipantData().withChatParticipantId(aChatParticipantId().withChatId(chatId).build()).build());

        when(chatParticipantDataSource.findAllByChatId(chatId)).thenReturn(existingParticipants);

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(chatId, newParticipants);

        assertFalse(result.hasDeletions());
        assertTrue(result.getDeletions().isEmpty());
    }

    @Test
    public void detectChanges_WithExistingChatParticipantsAndNoNewParticipants_ReturnsAllExistingAsDeletions() {
        UUID chatId = UUID.randomUUID();
        ChatParticipantData existingParticipantData1 = aChatParticipantData().withChatParticipantId(aChatParticipantId().withChatId(chatId).build()).build();
        ChatParticipantData existingParticipantData2 = aChatParticipantData().withChatParticipantId(aChatParticipantId().withChatId(chatId).build()).build();

        List<ChatParticipantData> existingParticipants = Arrays.asList(existingParticipantData1, existingParticipantData2);

        List<ChatParticipantData> newParticipants = new ArrayList<>();

        when(chatParticipantDataSource.findAllByChatId(chatId)).thenReturn(existingParticipants);

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(chatId, newParticipants);

        assertTrue(result.hasDeletions());
        List<ChatParticipantData> deletedParticipants = result.getDeletions();
        assertFalse(deletedParticipants.isEmpty());

        assertTrue(deletedParticipants.contains(existingParticipantData1));
        assertTrue(deletedParticipants.contains(existingParticipantData2));
    }

    @Test
    public void detectChanged_WithNoExistingChatParticipantsButWithNewParticipants_ReturnsAllNewAdditions() {
        UUID chatId = UUID.randomUUID();
        ChatParticipantData newChatParticipantData1 = aChatParticipantData().withChatParticipantId(aChatParticipantId().build()).build();
        ChatParticipantData newChatParticipantData2 = aChatParticipantData().withChatParticipantId(aChatParticipantId().build()).build();

        List<ChatParticipantData> existingParticipants = new ArrayList<>();
        List<ChatParticipantData> newParticipants = Arrays.asList(newChatParticipantData1, newChatParticipantData2);

        when(chatParticipantDataSource.findAllByChatId(chatId)).thenReturn(existingParticipants);

        ChangeResult<ChatParticipantData> result = chatParticipantChangeDetector.detectChanges(chatId, newParticipants);

        assertTrue(result.hasAdditions());
        List<ChatParticipantData> addedParticipants = result.getAdditions();

        assertFalse(addedParticipants.isEmpty());

        assertTrue(addedParticipants.contains(newChatParticipantData1));
        assertTrue(addedParticipants.contains(newChatParticipantData2));
    }
}
