package com.chat_app.application.service;

import com.chat_app.application.dto.MessageDto;
import com.chat_app.domain.entity.Message;
import com.chat_app.domain.factory.IdFactory;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.MessageId;
import com.chat_app.domain.valueobjects.SenderId;
import com.chat_app.infrastructure.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageDto sendMessage(
            ChatId chatId,
            SenderId senderId,
            String content,
            OffsetDateTime timestamp
    ) {
        Message message = new Message(
                IdFactory.generateId(MessageId::new),
                chatId,
                senderId,
                content,
                timestamp
        );

//        messageRepository.save(message);

        return MessageDto.from(message);
    }
}
