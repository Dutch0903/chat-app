package com.chat_app.domain.entity;

import com.chat_app.domain.exception.CannotAddParticipantToPrivateChatException;
import com.chat_app.domain.exception.CannotRemoveParticipantFromPrivateChatException;
import com.chat_app.domain.exception.ChatParticipantLimitExceededException;
import com.chat_app.domain.exception.ParticipantAlreadyInChatException;
import com.chat_app.domain.type.ChatType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.chat_app.testdata.ChatBuilder.aChat;
import static com.chat_app.testdata.ParticipantBuilder.aParticipant;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChatTest {

    @Test
    public void addParticipant_WhenChatIsPrivate_ThrowsException() {
        Chat chat = aChat().withChatType(ChatType.PRIVATE).build();
        Participant participant = aParticipant().build();

        assertThrows(CannotAddParticipantToPrivateChatException.class, () -> chat.addParticipant(participant));
    }

    @Test
    public void addParticipant_WhenChatIsGroup_DoesNotThrowException() {
        Chat chat = aChat().withChatType(ChatType.GROUP).build();
        Participant participant = aParticipant().build();

        chat.addParticipant(participant);
    }

    @Test
    public void addParticipant_WhenAlreadyInChat_ThrowsException() {
        Participant participant = aParticipant().build();
        Chat chat = aChat().withChatType(ChatType.GROUP).withParticipants(List.of(participant)).build();

        assertThrows(ParticipantAlreadyInChatException.class, () -> chat.addParticipant(participant));
    }

    @Test
    public void addParticipant_WhenChatParticipantLimitExceeded_ThrowsException() {
        Participant participant = aParticipant().build();
        Chat chat = aChat()
                .withChatType(ChatType.GROUP)
                .withParticipants(IntStream.range(0, 9).mapToObj(i -> aParticipant().build()).toList())
                .build();

        assertThrows(ChatParticipantLimitExceededException.class, () -> chat.addParticipant(participant));
    }

    @Test
    public void removeParticipant_WhenChatIsPrivate_ThrowsException() {
        Chat chat = aChat().withChatType(ChatType.PRIVATE).build();
        Participant participant = aParticipant().build();

        assertThrows(CannotRemoveParticipantFromPrivateChatException.class, () -> chat.removeParticipant(participant));
    }

    @Test
    public void removeParticipant_WhenParticipantIsNotInChat_DoesNothing() {
        Chat chat = aChat().withChatType(ChatType.GROUP).build();
        Participant participant = aParticipant().build();

        Integer originalSize = chat.getParticipants().size();

        chat.removeParticipant(participant);

        assertEquals(originalSize, chat.getParticipants().size());
    }

    @Test
    public void removeParticipant_When_ParticipantIsInChat_DoesRemoveParticipant() {
        Participant participant = aParticipant().build();
        Chat chat = aChat().withChatType(ChatType.GROUP)
                .withParticipants(new ArrayList<>(List.of(participant)))
                .build();

        chat.removeParticipant(participant);

        assertTrue(chat.getParticipants().isEmpty());
    }
}
