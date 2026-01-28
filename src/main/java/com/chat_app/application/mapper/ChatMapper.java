package com.chat_app.application.mapper;

import com.chat_app.application.dto.ChatDetailsDto;
import com.chat_app.application.dto.ChatDto;
import com.chat_app.presentation.enums.ChatType;
import com.chat_app.presentation.response.ChatDetailsResponse;
import com.chat_app.presentation.response.ChatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(source = "type", target = "type")
    ChatResponse toChatResponse(ChatDto chatDto);

    @Mapping(source = "type", target = "type")
    ChatDetailsResponse toChatDetailsResponse(ChatDetailsDto chatDetailsDto);
}
