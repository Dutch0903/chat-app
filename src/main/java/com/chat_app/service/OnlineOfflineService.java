package com.chat_app.service;

import com.chat_app.entity.User;
import com.chat_app.repository.UserRepository;
import com.chat_app.security.UserDetailsImpl;
import com.chat_app.valueobjects.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
@Slf4j
public class OnlineOfflineService {
    private final Set<UserId> onlineUsers;
    private final UserRepository userRepository;

    public OnlineOfflineService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.onlineUsers = new ConcurrentSkipListSet<>();
    }

    public void addOnlineUser(Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);

        log.info("{} is online", userDetails.getUsername());
        onlineUsers.add(userDetails.getId());
    }

    public void removeOnlineUser(Principal principal) {
        UserDetailsImpl userDetails = getUserDetails(principal);
        log.info("{} went offline", userDetails.getUsername());
        onlineUsers.remove(userDetails.getId());
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
