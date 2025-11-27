package com.chat_app.service;

import com.chat_app.dto.RegistrationData;
import com.chat_app.entity.User;
import com.chat_app.exception.UsernameOrEmailAlreadyInUseException;
import com.chat_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegistrationData registrationData) throws UsernameOrEmailAlreadyInUseException {
        if (userRepository.exists(registrationData.username(), registrationData.email())) {
            throw new UsernameOrEmailAlreadyInUseException();
        }

        User user = new User(registrationData.username(), registrationData.email(), registrationData.roles(), passwordEncoder.encode(registrationData.password()));

        userRepository.save(user);
    }
}
