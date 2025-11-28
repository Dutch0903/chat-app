package com.chat_app.controller;

import com.chat_app.dto.RegistrationData;
import com.chat_app.exception.UsernameOrEmailAlreadyInUseException;
import com.chat_app.request.LoginRequest;
import com.chat_app.request.RegisterRequest;
import com.chat_app.response.JwtResponse;
import com.chat_app.security.JwtUtils;
import com.chat_app.security.UserDetailsImpl;
import com.chat_app.service.RegistrationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles =
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        JwtResponse jwtResponse =
                JwtResponse.builder()
                        .token(token)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .roles(roles)
                        .build();

        Cookie accessTokenCookie = new Cookie("access_token", token);
        accessTokenCookie.setHttpOnly(true);

        response.addCookie(accessTokenCookie);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws UsernameOrEmailAlreadyInUseException {
        RegistrationData registrationData = registerRequest.getRegistrationData();

        this.registrationService.register(registrationData);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
