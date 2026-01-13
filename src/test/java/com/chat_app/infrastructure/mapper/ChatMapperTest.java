package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.ChatParticipant;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.chat_app.testdata.ChatBuilder.aChat;
import static com.chat_app.testdata.ChatDataBuilder.aChatData;
import static com.chat_app.testdata.ChatParticipantBuilder.aChatParticipant;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ChatMapperTest {

    @InjectMocks
    private ChatMapper chatMapper;

    @Test
    public void toEntity_ReturnsCorrectEntity() {
        ChatData chatData = aChatData().build();

        List<ChatParticipant> chatParticipants = Arrays.asList(
                aChatParticipant().build(),
                aChatParticipant().build()
        );

        Chat chat = chatMapper.toEntity(chatData, chatParticipants);

        assertEquals(chatData.getId(), chat.getId().value());
        assertEquals(chatData.getType(), chat.getType().name());
        assertEquals(chatData.getName(), chat.getName());
        assertEquals(chatData.getCreatedAt(), chat.getCreatedAt());
        assertEquals(chatData.getUpdatedAt(), chat.getUpdatedAt());

        assertEquals(chatParticipants, chat.getParticipants());
    }

    @Test
    public void toData_ReturnsCorrectData() {
        Chat chat = aChat().build();

        ChatData data = chatMapper.toData(chat, false);

        assertEquals(chat.getId().value(), data.getId());
        assertEquals(chat.getType().name(), data.getType());
        assertEquals(chat.getName(), data.getName());
        assertEquals(chat.getCreatedAt(), data.getCreatedAt());
        assertEquals(chat.getUpdatedAt(), data.getUpdatedAt());
    }
}
