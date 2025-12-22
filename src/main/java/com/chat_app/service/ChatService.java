package com.chat_app.service;

import com.chat_app.dto.ChatDetailsDto;
import com.chat_app.dto.ChatDto;
import com.chat_app.entity.Chat;
import com.chat_app.entity.ChatParticipant;
import com.chat_app.exception.ChatNotFoundException;
import com.chat_app.exception.ForbiddenException;
import com.chat_app.factory.IdFactory;
import com.chat_app.repository.ChatParticipantRepository;
import com.chat_app.repository.ChatRepository;
import com.chat_app.type.ChatParticipantRole;
import com.chat_app.type.ChatType;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    public List<ChatDto> getAllChats(ParticipantId participantId) {
        List<Chat> chats = this.chatRepository.getAllChats(participantId);

        return chats.stream().map(ChatDto::from).toList();
    }

    public ChatDetailsDto getChatDetails(ChatId chatId, ParticipantId participantId) {
        Chat chat = chatRepository.findChatByIdAndParticipantId(chatId, participantId);

        if (chat == null) {
            throw new ForbiddenException("No chat was found for chat id " + chatId.value() + " and participant id " + participantId.value());
        }

        List<ChatParticipant> participants = chatParticipantRepository.getAllChatParticipants(chatId);

        return ChatDetailsDto.from(chat, participants);
    }

    public ChatDetailsDto startDirectChat(ParticipantId starter, ParticipantId participantId) {
        Chat newChat = new Chat(
                IdFactory.generateId(ChatId::new),
                ChatType.DIRECT
        );

        chatRepository.insert(newChat);

        List<ChatParticipant> participants = new ArrayList<>();

        participants.add(addParticipantToChat(newChat, starter));
        participants.add(addParticipantToChat(newChat, participantId));

        return ChatDetailsDto.from(newChat, participants);
    }

    public ChatDetailsDto startGroupChat(String name, List<ParticipantId> participantIds) {
        Chat newChat = new Chat(
                IdFactory.generateId(ChatId::new),
                ChatType.GROUP,
                name
        );

        chatRepository.insert(newChat);

        List<ChatParticipant> participants = new ArrayList<>();
        participantIds.forEach(participantId -> participants.add(addParticipantToChat(newChat, participantId)));

        return ChatDetailsDto.from(newChat, participants);
    }

    public ChatParticipant addParticipantToChat(Chat chat, ParticipantId participantId) {
        ChatParticipant chatParticipant = new ChatParticipant(
                chat.getId(),
                participantId,
                ChatParticipantRole.MEMBER
        );

        chatParticipantRepository.insert(chatParticipant);

        return chatParticipant;
    }

    public ChatParticipant addParticipantToChat(ChatId chatId, ParticipantId participantId) {
        Chat chat = chatRepository.getChatById(chatId);

        if (chat == null) {
            throw new ChatNotFoundException();
        }

        return addParticipantToChat(chat, participantId);
    }
}
