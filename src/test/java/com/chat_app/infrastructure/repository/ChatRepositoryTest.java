package com.chat_app.infrastructure.repository;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.exception.ChatCreationException;
import com.chat_app.domain.enums.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.mapper.ChatMapper;
import com.chat_app.infrastructure.repository.jdbc.ChatDataSource;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;
import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.chat_app.testdata.ChatBuilder.aChat;
import static com.chat_app.testdata.ChatDataBuilder.aChatData;
import static com.chat_app.testdata.ChatIdBuilder.aChatId;
import static com.chat_app.testdata.ParticipantBuilder.aParticipant;
import static com.chat_app.testdata.ChatParticipantDataBuilder.aChatParticipantData;
import static com.chat_app.testdata.ParticipantIdBuilder.aParticipantId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatRepositoryTest {

    @Mock
    private ChatMapper chatMapper;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ChatDataSource chatDataSource;

    @InjectMocks
    private ChatRepository chatRepository;

    @Test
    public void getAllChats_ParticipantHasChats_ReturnsChat() {
        ParticipantId participantId = aParticipantId().build();

        ChatId chatId1 = aChatId().build();
        ChatId chatId2 = aChatId().build();

        ParticipantData participantData1 = aChatParticipantData().withChatId(chatId1.value()).withParticipantId(participantId.value()).build();
        ParticipantData participantData2 = aChatParticipantData().withChatId(chatId2.value()).withParticipantId(participantId.value()).build();

        Participant participant1 = aParticipant().withChatId(chatId1).withParticipantId(participantId).build();
        Participant participant2 = aParticipant().withChatId(chatId2).withParticipantId(participantId).build();

        ChatData chatData1 = aChatData().withId(chatId1.value()).build();
        ChatData chatData2 = aChatData().withId(chatId2.value()).build();

        Chat chat1 = aChat().withChatId(chatId1).withParticipants(Collections.singletonList(participant1)).build();
        Chat chat2 = aChat().withChatId(chatId2).withParticipants(Collections.singletonList(participant2)).build();

        when(participantRepository.getAllParticipatingChats(participantId)).thenReturn(Arrays.asList(participantData1, participantData2));

        when(participantRepository.getAllChatParticipants(Arrays.asList(chatId1.value(), chatId2.value()))).thenReturn(
                Arrays.asList(participant1, participant2)
        );

        when(chatDataSource.findAllById(Arrays.asList(chatId1.value(), chatId2.value())))
                .thenReturn(Arrays.asList(chatData1, chatData2));

        when(chatMapper.toEntity(any(ChatData.class), anyList())).thenReturn(chat1, chat2);

        List<Chat> result = chatRepository.getAllChats(participantId);

        assertEquals(2, result.size());

        assertEquals(chat1, result.get(0));
        assertEquals(chat2, result.get(1));
    }

    @Test
    public void getAllChats_ParticipantHasNoChats_ReturnsEmptyList() {
        ParticipantId participantId = aParticipantId().build();

        when(participantRepository.getAllParticipatingChats(participantId)).thenReturn(Collections.emptyList());

        List<Chat> chats = chatRepository.getAllChats(participantId);

        assertEquals(0, chats.size());
    }

    @Test
    public void findChatByIdAndParticipantId_NoParticipants_ReturnsEmptyList() {
        ChatId chatId = aChatId().build();
        ParticipantId participantId = aParticipantId().build();

        when(participantRepository.existsByChatIdAndParticipantId(chatId, participantId)).thenReturn(Boolean.FALSE);

        assertNull(chatRepository.findChatByIdAndParticipantId(chatId, participantId));
    }

    @Test
    public void findChatByIdAndParticipantId_ParticipatingButChatParticipantNotFound_ReturnsNull() {
        ParticipantId participantId = aParticipantId().build();
        ChatId chatId = aChatId().build();

        when(participantRepository.existsByChatIdAndParticipantId(chatId, participantId)).thenReturn(Boolean.TRUE);

        when(chatDataSource.findById(chatId.value())).thenReturn(Optional.empty());

        assertNull(chatRepository.findChatByIdAndParticipantId(chatId, participantId));
    }

    @Test
    public void findChatByIdAndParticipantId_ParticipatingAndChatParticipantFound_ReturnsChat() {
        ParticipantId participantId = aParticipantId().build();
        ChatId chatId = aChatId().build();

        ChatData chatData = aChatData().withId(chatId.value()).build();
        List<Participant> participants = Arrays.asList(
                aParticipant().build(),
                aParticipant().build()
        );

        when(participantRepository.existsByChatIdAndParticipantId(chatId, participantId)).thenReturn(Boolean.TRUE);
        when(chatDataSource.findById(chatId.value())).thenReturn(Optional.of(chatData));
        when(participantRepository.getAllChatParticipants(chatId)).thenReturn(participants);
        when(chatMapper.toEntity(chatData, participants)).thenReturn(
                aChat().withChatId(chatId)
                        .withName(chatData.getName())
                        .withChatType(ChatType.valueOf(chatData.getType()))
                        .withParticipants(participants)
                        .build()
        );

        Chat chat = chatRepository.findChatByIdAndParticipantId(chatId, participantId);

        assertNotNull(chat);

        assertEquals(chatId, chat.getId());
        assertEquals(chatData.getName(), chat.getName());
        assertEquals(chatData.getType(), chat.getType().name());
        assertEquals(participants, chat.getParticipants());
    }

    @Test
    public void existsPrivateChatBetweenParticipants_ChatNotFound_ReturnsFalse() {
        ParticipantId participantId1 = aParticipantId().build();
        ParticipantId participantId2 = aParticipantId().build();

        when(chatDataSource.existsPrivateChat(participantId1.value(), participantId2.value())).thenReturn(Boolean.FALSE);

        assertFalse(chatRepository.existsPrivateChatBetweenParticipants(participantId1, participantId2));
    }

    @Test
    public void existsPrivateChatBetweenParticipants_ChatFound_ReturnsTrue() {
        ParticipantId participantId1 = aParticipantId().build();
        ParticipantId participantId2 = aParticipantId().build();

        when(chatDataSource.existsPrivateChat(participantId1.value(), participantId2.value())).thenReturn(true);

        assertTrue(chatRepository.existsPrivateChatBetweenParticipants(participantId1, participantId2));
    }

    @Test
    public void insert_savesChatAndParticipants() {
        Chat chat = aChat().build();
        ChatData chatData = aChatData().withId(chat.getId().value()).build();

        when(chatMapper.toData(chat, true)).thenReturn(chatData);

        chatRepository.insert(chat);

        verify(chatDataSource, times(1)).save(chatData);
        verify(participantRepository, times(1)).saveAll(chat.getParticipants());
    }

    @Test
    public void insert_throwsExceptionOnDuplicateChatId() {
        Chat chat = aChat().build();
        ChatData chatData = aChatData().withId(chat.getId().value()).build();

        when(chatMapper.toData(chat, true)).thenReturn(chatData);
        when(chatDataSource.save(chatData)).thenThrow(new DataIntegrityViolationException("Message"));

        assertThrows(ChatCreationException.class, () -> chatRepository.insert(chat));
    }
}
