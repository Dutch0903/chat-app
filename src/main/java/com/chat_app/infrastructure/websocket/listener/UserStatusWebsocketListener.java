package com.chat_app.infrastructure.websocket.listener;

import com.chat_app.domain.event.UserWentOfflineEvent;
import com.chat_app.domain.event.UserWentOnlineEvent;
import com.chat_app.infrastructure.websocket.Topics;
import com.chat_app.infrastructure.websocket.message.UserOfflineMessage;
import com.chat_app.infrastructure.websocket.message.UserOnlineMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatusWebsocketListener {
    private final SimpMessageSendingOperations messaging;

    @EventListener
    public void onUserWentOnline(UserWentOnlineEvent event) {
        messaging.convertAndSend(
                Topics.USER_ONLINE,
                UserOnlineMessage.create(
                        event.userId().value(),
                        event.timestamp().toEpochMilli()
                )
        );
    }

    @EventListener
    public void onUserWentOffline(UserWentOfflineEvent event) {
        messaging.convertAndSend(
                Topics.USER_OFFLINE,
                UserOfflineMessage.create(
                        event.userId().value(),
                        event.timestamp().toEpochMilli()
                )
        );
    }
}
