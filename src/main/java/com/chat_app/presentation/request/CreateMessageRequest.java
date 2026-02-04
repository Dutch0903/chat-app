package com.chat_app.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateMessageRequest {
    private String content;
    private OffsetDateTime timestamp;
}
