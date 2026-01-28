package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.enums.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMapper {

    public Chat toEntity(ChatData chatData, List<Participant> participants) {
        return new Chat(
                ChatId.from(chatData.getId()),
                ChatType.valueOf(chatData.getType()),
                chatData.getName(),
                participants,
                chatData.getCreatedAt(),
                chatData.getUpdatedAt()
        );
    }

    public ChatData toData(Chat chat, boolean isNew) {
        return new ChatData(
                chat.getId().value(),
                chat.getType().name(),
                chat.getName(),
                chat.getCreatedAt(),
                chat.getUpdatedAt(),
                isNew
        );
    }
}
