package com.tpdev.userService.controller;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.userService.dto.AuthResponse;
import com.tpdev.userService.dto.LoginRequest;
import com.tpdev.userService.dto.RefreshTokenRequest;
import com.tpdev.userService.dto.RegisterRequest;
import com.tpdev.userService.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiConstants.REGISTER)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("POST " + ApiConstants.AUTH + ApiConstants.REGISTER + " — username={}", request.getUsername());
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping(ApiConstants.LOGIN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("POST " + ApiConstants.AUTH + ApiConstants.LOGIN + " — username={}", request.getUsername());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(ApiConstants.REFRESH)
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("POST " + ApiConstants.AUTH + ApiConstants.REFRESH);
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping(ApiConstants.LOGOUT)
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST " + ApiConstants.AUTH + ApiConstants.LOGOUT + " — username={}", userDetails.getUsername());
        authService.logout(userDetails.getUsername());
        return ResponseEntity.ok("Logged out successfully");
    }
}
