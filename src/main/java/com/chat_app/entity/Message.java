package com.chat_app.entity;

import com.chat_app.type.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private UUID id;

    private String username;
    private String content;
    private MessageType type;
}
