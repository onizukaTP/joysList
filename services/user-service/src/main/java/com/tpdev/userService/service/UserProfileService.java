package com.tpdev.userService.service;

import com.tpdev.userService.dto.UpdateProfileRequest;
import com.tpdev.userService.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfileDto getProfile(Long userId);

    UserProfileDto updateProfile(Long userId, UpdateProfileRequest request, String callerUsername);

    String uploadAvatar(Long userId, MultipartFile file, String callerUsername);

    void deleteAvatar(Long userId, String callerUsername);
}
