package com.chat_app.application.service;

import com.chat_app.infrastructure.Message;
import com.chat_app.domain.entity.User;
import com.chat_app.infrastructure.repository.UserRepository;
import com.chat_app.infrastructure.security.UserDetailsImpl;
import com.chat_app.domain.type.MessageType;
import com.chat_app.domain.valueobjects.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
@Slf4j
public class OnlineOfflineService {
    private final Set<UserId> onlineUsers;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public OnlineOfflineService(UserRepository userRepository, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.userRepository = userRepository;
        this.onlineUsers = new ConcurrentSkipListSet<>();
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    public void addOnlineUser(Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);

        onlineUsers.add(userDetails.getId());

        this.simpMessageSendingOperations.convertAndSend("/topic/online",
                Message.builder()
                        .type(MessageType.USER_ONLINE)
                        .username(userDetails.getUsername())
                        .build()
        );
        log.info("{} is online", userDetails.getUsername());
    }

    public void removeOnlineUser(Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);
        log.info("{} went offline", userDetails.getUsername());
        onlineUsers.remove(userDetails.getId());

        this.simpMessageSendingOperations.convertAndSend("/topic/online",
                Message.builder()
                        .type(MessageType.USER_OFFLINE)
                        .username(userDetails.getUsername())
                        .build()
        );
    }

    public List<User> getOnlineUsers() {
        return this.userRepository.findAllByIdIn(this.onlineUsers);
    }

    private UserDetailsImpl getUserDetails(Principal principal) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
        Object object = user.getPrincipal();
        return (UserDetailsImpl) object;
    }
}
