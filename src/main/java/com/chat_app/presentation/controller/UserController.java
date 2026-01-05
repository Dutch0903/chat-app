package com.chat_app.presentation.controller;

import com.chat_app.domain.entity.User;
import com.chat_app.presentation.response.UserResponse;
import com.chat_app.application.service.OnlineOfflineService;
import com.chat_app.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OnlineOfflineService onlineOfflineService;

    @GetMapping("")
    public ResponseEntity<?> get() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users.stream().map(UserResponse::from));
    }

    @GetMapping("/online")
    public ResponseEntity<?> getOnlineUsers() {
        List<User> onlineUsers = onlineOfflineService.getOnlineUsers();

        return ResponseEntity.ok(onlineUsers.stream().map(UserResponse::from));
    }
}
