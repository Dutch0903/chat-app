package com.chat_app.controller;

import com.chat_app.entity.User;
import com.chat_app.response.UserResponse;
import com.chat_app.service.OnlineOfflineService;
import com.chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        return ResponseEntity.ok(users.stream().map(UserResponse::create));
    }

    @GetMapping("/online")
    public ResponseEntity<?> getOnlineUsers() {
        List<User> onlineUsers = onlineOfflineService.getOnlineUsers();

        return ResponseEntity.ok(onlineUsers.stream().map(UserResponse::create));
    }
}
