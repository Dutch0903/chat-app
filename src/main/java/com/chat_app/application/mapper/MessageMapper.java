package com.chat_app.application.mapper;

import com.chat_app.application.dto.MessageDto;
import com.chat_app.presentation.response.MessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageResponse toMessageResponse(MessageDto messageDto);
}
