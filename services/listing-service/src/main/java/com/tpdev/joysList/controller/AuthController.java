package com.tpdev.joysList.controller;

import com.tpdev.events.UserRegisteredEvent;
import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.dto.AuthResponse;
import com.tpdev.joysList.dto.LoginRequest;
import com.tpdev.joysList.dto.RegisterRequest;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.entity.enums.Role;
import com.tpdev.joysList.kafka.producer.UserEventProducer;
import com.tpdev.joysList.repo.UserRepository;
import com.tpdev.joysList.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserEventProducer userEventProducer;

    @PostMapping(ApiConstants.LOGIN)
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest request) {
        log.info("Login request received: {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        log.info("User, {} logged in.", user.getUsername());

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping(ApiConstants.REGISTER_USER)
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity user = getUserEntity(request);

        userRepository.save(user);
        log.info("User: {} got created.", user.getUsername());

        userEventProducer.publishUserRegistered(
                new UserRegisteredEvent(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                )
        );
        log.info("Published User Registered Event");

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }

    private UserEntity getUserEntity(RegisterRequest request) {
        UserEntity user = new UserEntity();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);
        return user;
    }

    @DeleteMapping(ApiConstants.DELETE_USER)
    public ResponseEntity<String> deleteUser(@RequestBody UserEntity userEntity) {
        userRepository.delete(userEntity);
        return ResponseEntity.ok("Successfully deleted " + userEntity.getUsername());
    }
}
