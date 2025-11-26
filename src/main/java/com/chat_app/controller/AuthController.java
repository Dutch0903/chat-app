package com.chat_app.controller;

import com.chat_app.dto.RegistrationData;
import com.chat_app.exception.UsernameOrEmailAlreadyInUseException;
import com.chat_app.request.RegisterRequest;
import com.chat_app.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegistrationData registrationData = registerRequest.getRegistrationData();

        try {
            this.registrationService.register(registrationData);
        } catch (UsernameOrEmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body("Username or Email is already in use!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
