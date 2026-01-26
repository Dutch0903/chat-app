package com.chat_app.application.service;

import com.chat_app.domain.event.UserWentOfflineEvent;
import com.chat_app.domain.event.UserWentOnlineEvent;
import com.chat_app.domain.valueobjects.UserId;
import com.chat_app.infrastructure.security.UserDetailsImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineOfflineService {
    private final Set<UserId> onlineUsers = ConcurrentHashMap.newKeySet();
    private final ApplicationEventPublisher eventPublisher;

    public OnlineOfflineService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void addOnlineUser(Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);
        onlineUsers.add(userDetails.getId());

        eventPublisher.publishEvent(
                new UserWentOnlineEvent(userDetails.getId(), Instant.now())
        );
    }

    public void removeOnlineUser(Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);
        onlineUsers.remove(userDetails.getId());

        eventPublisher.publishEvent(
                new UserWentOfflineEvent(userDetails.getId(), Instant.now())
        );
    }

    public List<UserId> getOnlineUsers() {
        return this.onlineUsers.stream().toList();
    }

    private UserDetailsImpl getUserDetails(Principal principal) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
        Object object = user.getPrincipal();
        return (UserDetailsImpl) object;
    }
}
