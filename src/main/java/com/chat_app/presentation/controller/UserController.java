package com.chat_app.presentation.controller;

import com.chat_app.application.service.OnlineOfflineService;
import com.chat_app.application.service.UserService;
import com.chat_app.domain.entity.User;
import com.chat_app.domain.valueobjects.UserId;
import com.chat_app.presentation.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OnlineOfflineService onlineOfflineService;

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users.stream().map(UserResponse::from).toList());
    }

    @GetMapping("/online")
    public ResponseEntity<List<UUID>> getOnlineUsers() {
        List<UserId> onlineUsers = onlineOfflineService.getOnlineUsers();

        return ResponseEntity.ok(onlineUsers.stream().map(UserId::value).toList());
    }
}
