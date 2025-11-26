package com.chat_app.repository;

import com.chat_app.entity.User;

public interface UserRepository {
    boolean exists(String username, String email);

    int save(User user);
}
