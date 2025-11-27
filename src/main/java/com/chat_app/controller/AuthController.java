package com.chat_app.controller;

import com.chat_app.dto.RegistrationData;
import com.chat_app.exception.UsernameOrEmailAlreadyInUseException;
import com.chat_app.request.LoginRequest;
import com.chat_app.request.RegisterRequest;
import com.chat_app.security.JwtUtils;
import com.chat_app.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws UsernameOrEmailAlreadyInUseException {
        RegistrationData registrationData = registerRequest.getRegistrationData();

        this.registrationService.register(registrationData);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
