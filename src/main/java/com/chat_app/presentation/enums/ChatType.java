package com.chat_app.presentation.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ChatType",
        description = "Type of chat",
        enumAsRef = true
)
public enum ChatType {
    @Schema(description = "Private message chat")
    PRIVATE,

    @Schema(description = "Group chat")
    GROUP
}
