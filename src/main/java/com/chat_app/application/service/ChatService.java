package com.chat_app.application.service;

import com.chat_app.application.dto.ChatDetailsDto;
import com.chat_app.application.dto.ChatDto;
import com.chat_app.domain.entity.Chat;
import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.exception.ChatAlreadyExistsException;
import com.chat_app.domain.exception.ForbiddenException;
import com.chat_app.domain.factory.IdFactory;
import com.chat_app.domain.type.ParticipantRole;
import com.chat_app.domain.type.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public List<ChatDto> getAllChats(ParticipantId participantId) {
        List<Chat> chats = this.chatRepository.getAllChats(participantId);

        return chats.stream().map(ChatDto::from).toList();
    }

    public ChatDetailsDto getChatDetails(ChatId chatId, ParticipantId participantId) {
        Chat chat = chatRepository.findChatByIdAndParticipantId(chatId, participantId);

        if (chat == null) {
            throw new ForbiddenException("No chat was found for chat id " + chatId.value() + " and participant id " + participantId.value());
        }

        return ChatDetailsDto.from(chat);
    }

    public ChatDetailsDto startPrivateChat(ParticipantId starter, ParticipantId participantId) {
        if (chatRepository.existsPrivateChatBetweenParticipants(starter, participantId)) {
            throw new ChatAlreadyExistsException();
        }
        ChatId chatId = IdFactory.generateId(ChatId::new);

        Chat newChat = new Chat(
                chatId,
                ChatType.PRIVATE,
                null,
                Arrays.asList(
                        new Participant(chatId, starter, ParticipantRole.MEMBER),
                        new Participant(chatId, participantId, ParticipantRole.MEMBER)
                )
        );

        chatRepository.insert(newChat);

        return ChatDetailsDto.from(newChat);
    }

    public ChatDetailsDto startGroupChat(String name, List<ParticipantId> participantIds) {
        ChatId chatId = IdFactory.generateId(ChatId::new);


        Chat newChat = new Chat(
                chatId,
                ChatType.GROUP,
                name,
                participantIds.stream()
                        .map(participantId -> new Participant(chatId, participantId, ParticipantRole.MEMBER))
                        .toList()
        );

        chatRepository.insert(newChat);

        return ChatDetailsDto.from(newChat);
    }
}
