package com.chat_app.presentation.listener;

import com.chat_app.application.service.OnlineOfflineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final OnlineOfflineService onlineOfflineService;

    @EventListener
    public void handleConnectedEvent(SessionConnectedEvent sessionConnectedEvent) {
        onlineOfflineService.addOnlineUser(sessionConnectedEvent.getUser());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        onlineOfflineService.removeOnlineUser(sessionDisconnectEvent.getUser());
    }
}
