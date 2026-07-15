package com.tpdev.userService.service;

import com.tpdev.userService.dto.AuthResponse;
import com.tpdev.userService.dto.LoginRequest;
import com.tpdev.userService.dto.RefreshTokenRequest;
import com.tpdev.userService.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(RefreshTokenRequest request);

    void logout(String username);
}
