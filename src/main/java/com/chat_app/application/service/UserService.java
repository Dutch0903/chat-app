package com.chat_app.application.service;

import com.chat_app.domain.entity.User;
import com.chat_app.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().iterator().forEachRemaining(users::add);

        return users;
    }
}
