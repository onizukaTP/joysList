package com.tpdev.userService.controller;

import com.tpdev.joysList.constants.ApiConstants;
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
@RequestMapping(ApiConstants.USERS)
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping(ApiConstants.PROFILE)
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable Long id) {
        log.info("GET " + ApiConstants.USERS + ApiConstants.PROFILE.replace("{id}", String.valueOf(id)));
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @PutMapping(ApiConstants.PROFILE)
    public ResponseEntity<UserProfileDto> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal UserDetails caller) {
        log.info("PUT " + ApiConstants.USERS + ApiConstants.PROFILE.replace("{id}", String.valueOf(id)) + " — caller={}", caller.getUsername());
        return ResponseEntity.ok(userProfileService.updateProfile(id, request, caller.getUsername()));
    }

    @PostMapping(ApiConstants.AVATAR)
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails caller) {
        log.info("POST " + ApiConstants.USERS + ApiConstants.AVATAR.replace("{id}", String.valueOf(id)) + " — caller={}", caller.getUsername());
        String avatarUrl = userProfileService.uploadAvatar(id, file, caller.getUsername());
        return ResponseEntity.ok(Map.of("avatarUrl", avatarUrl));
    }

    @DeleteMapping(ApiConstants.AVATAR)
    public ResponseEntity<String> deleteAvatar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails caller) {
        log.info("DELETE " + ApiConstants.USERS + ApiConstants.AVATAR.replace("{id}", String.valueOf(id)) + " — caller={}", caller.getUsername());
        userProfileService.deleteAvatar(id, caller.getUsername());
        return ResponseEntity.ok("Avatar removed successfully");
    }

    @GetMapping(ApiConstants.ID)
    public ResponseEntity<UserProfileDto> getPublicProfile(@PathVariable Long id) {
        log.info("GET " + ApiConstants.USERS + "/" + id);
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }
}
