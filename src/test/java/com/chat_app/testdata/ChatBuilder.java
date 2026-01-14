package com.chat_app.testdata;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.type.ChatType;
import com.chat_app.domain.valueobjects.ChatId;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.chat_app.testdata.ChatIdBuilder.aChatId;
import static com.chat_app.testdata.ParticipantBuilder.aParticipant;

public class ChatBuilder {
    private ChatId chatId = aChatId().build();
    private ChatType chatType = ChatType.PRIVATE;
    private String name = "Random Group name";
    private List<Participant> participants = new ArrayList<>(List.of(aParticipant().build()));
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public static ChatBuilder aChat() {
        return new ChatBuilder();
    }

    public ChatBuilder withChatId(ChatId chatId) {
        this.chatId = chatId;
        return this;
    }

    public ChatBuilder withChatType(ChatType chatType) {
        this.chatType = chatType;
        return this;
    }

    public ChatBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ChatBuilder withParticipants(List<Participant> participants) {
        this.participants = participants;
        return this;
    }

    public ChatBuilder withCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ChatBuilder withUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Chat build() {
        return new Chat(
                chatId,
                chatType,
                name,
                participants,
                createdAt,
                updatedAt
        );
    }
}
