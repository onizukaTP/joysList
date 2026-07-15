package com.tpdev.userService.service.impl;

import com.tpdev.events.UserRegisteredEvent;
import com.tpdev.userService.dto.AuthResponse;
import com.tpdev.userService.dto.LoginRequest;
import com.tpdev.userService.dto.RefreshTokenRequest;
import com.tpdev.userService.dto.RegisterRequest;
import com.tpdev.userService.entity.UserEntity;
import com.tpdev.userService.entity.UserProfile;
import com.tpdev.userService.entity.enums.Role;
import com.tpdev.userService.exception.DuplicateUserException;
import com.tpdev.userService.exception.InvalidTokenException;
import com.tpdev.userService.exception.UserNotFoundException;
import com.tpdev.userService.kafka.producer.UserEventProducer;
import com.tpdev.userService.repo.UserProfileRepository;
import com.tpdev.userService.repo.UserRepository;
import com.tpdev.userService.security.JwtService;
import com.tpdev.userService.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final String REFRESH_KEY_PREFIX = "user:refresh:";

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StringRedisTemplate redisTemplate;
    private final UserEventProducer userEventProducer;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration; // ms

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Duplicate checks
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("Email already in use: " + request.getEmail());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already taken: " + request.getUsername());
        }

        // Save user
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // Auto-create empty profile
        userProfileRepository.save(UserProfile.builder().user(user).build());

        // Publish Kafka event
        userEventProducer.publishUserRegistered(
                new UserRegisteredEvent(user.getId(), user.getUsername(), user.getEmail())
        );
        log.info("User registered: username={}", user.getUsername());

        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Authenticate via Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getUsername()));

        log.info("User logged in: username={}", user.getUsername());
        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        String incomingToken = request.getRefreshToken();

        // Look for any user whose refresh token matches
        // Pattern: user:refresh:{userId} -> refreshToken
        // We need to search — store bidirectional: also user:refresh:token:{token} -> userId
        String userIdStr = redisTemplate.opsForValue().get(REFRESH_KEY_PREFIX + "token:" + incomingToken);

        if (userIdStr == null) {
            throw new InvalidTokenException("Refresh token is invalid or expired");
        }

        Long userId = Long.parseLong(userIdStr);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Rotate: delete old, issue new
        redisTemplate.delete(REFRESH_KEY_PREFIX + userId);
        redisTemplate.delete(REFRESH_KEY_PREFIX + "token:" + incomingToken);

        log.info("Refresh token rotated for userId={}", userId);
        return buildAuthResponse(user);
    }

    @Override
    public void logout(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            String storedToken = redisTemplate.opsForValue().get(REFRESH_KEY_PREFIX + user.getId());
            if (storedToken != null) {
                redisTemplate.delete(REFRESH_KEY_PREFIX + user.getId());
                redisTemplate.delete(REFRESH_KEY_PREFIX + "token:" + storedToken);
            }
            log.info("User logged out: username={}", username);
        });
    }

    // -------------------------------------------------------
    // Helper — build AuthResponse and store refresh token
    // -------------------------------------------------------
    private AuthResponse buildAuthResponse(UserEntity user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken();
        long expiresIn = jwtService.getAccessTokenExpiration();

        // Store: userId -> refreshToken  AND  token -> userId (for lookup on refresh)
        long ttlSeconds = refreshExpiration / 1000;
        redisTemplate.opsForValue().set(
                REFRESH_KEY_PREFIX + user.getId(), refreshToken, ttlSeconds, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(
                REFRESH_KEY_PREFIX + "token:" + refreshToken,
                String.valueOf(user.getId()), ttlSeconds, TimeUnit.SECONDS);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
