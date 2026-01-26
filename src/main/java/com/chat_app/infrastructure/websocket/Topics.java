package com.chat_app.infrastructure.websocket;

public final class Topics {
    // Broadcast topics
    public static final String USER_OFFLINE = "/topic/user/offline";
    public static final String USER_ONLINE = "/topic/user/online";

    private Topics() {}
}
