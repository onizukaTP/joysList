package com.tpdev.userService.controller;

import com.tpdev.userService.dto.UpdateProfileRequest;
import com.tpdev.userService.dto.UserProfileDto;
import com.tpdev.userService.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable Long id) {
        log.info("GET /api/v1/users/{}/profile", id);
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserProfileDto> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal UserDetails caller) {
        log.info("PUT /api/v1/users/{}/profile — caller={}", id, caller.getUsername());
        return ResponseEntity.ok(userProfileService.updateProfile(id, request, caller.getUsername()));
    }

    @PostMapping("/{id}/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails caller) {
        log.info("POST /api/v1/users/{}/avatar — caller={}", id, caller.getUsername());
        String avatarUrl = userProfileService.uploadAvatar(id, file, caller.getUsername());
        return ResponseEntity.ok(Map.of("avatarUrl", avatarUrl));
    }

    @DeleteMapping("/{id}/avatar")
    public ResponseEntity<String> deleteAvatar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails caller) {
        log.info("DELETE /api/v1/users/{}/avatar — caller={}", id, caller.getUsername());
        userProfileService.deleteAvatar(id, caller.getUsername());
        return ResponseEntity.ok("Avatar removed successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getPublicProfile(@PathVariable Long id) {
        log.info("GET /api/v1/users/{}", id);
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }
}
