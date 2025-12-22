package com.chat_app.service;

import com.chat_app.dto.ChatDetailsDto;
import com.chat_app.entity.Chat;
import com.chat_app.entity.ChatParticipant;
import com.chat_app.exception.ChatNotFoundException;
import com.chat_app.exception.ForbiddenException;
import com.chat_app.repository.ChatParticipantRepository;
import com.chat_app.repository.ChatRepository;
import com.chat_app.dto.ChatDto;
import com.chat_app.type.ChatParticipantRole;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addParticipantToChat(ChatId chatId, ParticipantId participantId) {
        Chat chat = chatRepository.getChatById(chatId);

        if (chat == null) {
            throw new ChatNotFoundException();
        }

        ChatParticipant chatParticipant = new ChatParticipant(
                chat.getId(),
                participantId,
                ChatParticipantRole.MEMBER
        );

        chatParticipantRepository.save(chatParticipant);
    }
}
