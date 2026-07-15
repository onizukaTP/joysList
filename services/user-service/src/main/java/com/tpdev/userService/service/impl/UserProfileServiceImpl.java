package com.tpdev.userService.service.impl;

import com.tpdev.events.UserProfileUpdatedEvent;
import com.tpdev.s3.S3Service;
import com.tpdev.userService.dto.UpdateProfileRequest;
import com.tpdev.userService.dto.UserProfileDto;
import com.tpdev.userService.entity.UserEntity;
import com.tpdev.userService.entity.UserProfile;
import com.tpdev.userService.entity.enums.Role;
import com.tpdev.userService.exception.AccessDeniedException;
import com.tpdev.userService.exception.UserNotFoundException;
import com.tpdev.userService.kafka.producer.UserEventProducer;
import com.tpdev.userService.repo.UserProfileRepository;
import com.tpdev.userService.repo.UserRepository;
import com.tpdev.userService.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final S3Service s3Service;
    private final UserEventProducer userEventProducer;

    @Override
    public UserProfileDto getProfile(Long userId) {
        UserEntity user = findUserById(userId);
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.builder().user(user).build());

        return toDto(user, profile);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(Long userId, UpdateProfileRequest request, String callerUsername) {
        UserEntity user = findUserById(userId);
        assertOwnerOrAdmin(user, callerUsername);

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.builder().user(user).build());

        if (request.getBio() != null)      profile.setBio(request.getBio());
        if (request.getPhone() != null)    profile.setPhone(request.getPhone());
        if (request.getLocation() != null) profile.setLocation(request.getLocation());

        userProfileRepository.save(profile);

        userEventProducer.publishProfileUpdated(
                new UserProfileUpdatedEvent(userId, profile.getAvatarUrl(), profile.getLocation())
        );
        log.info("Profile updated for userId={}", userId);
        return toDto(user, profile);
    }

    @Override
    @Transactional
    public String uploadAvatar(Long userId, MultipartFile file, String callerUsername) {
        UserEntity user = findUserById(userId);
        assertOwnerOrAdmin(user, callerUsername);

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.builder().user(user).build());

        // Delete old avatar if present
        if (profile.getAvatarUrl() != null) {
            s3Service.deleteFile(profile.getAvatarUrl());
        }

        String avatarUrl = s3Service.uploadFile(file, "avatars");
        profile.setAvatarUrl(avatarUrl);
        userProfileRepository.save(profile);

        userEventProducer.publishProfileUpdated(
                new UserProfileUpdatedEvent(userId, avatarUrl, profile.getLocation())
        );
        log.info("Avatar uploaded for userId={}: {}", userId, avatarUrl);
        return avatarUrl;
    }

    @Override
    @Transactional
    public void deleteAvatar(Long userId, String callerUsername) {
        UserEntity user = findUserById(userId);
        assertOwnerOrAdmin(user, callerUsername);

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("Profile not found for userId: " + userId));

        if (profile.getAvatarUrl() != null) {
            s3Service.deleteFile(profile.getAvatarUrl());
            profile.setAvatarUrl(null);
            userProfileRepository.save(profile);
            log.info("Avatar deleted for userId={}", userId);
        }
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    private void assertOwnerOrAdmin(UserEntity targetUser, String callerUsername) {
        if (!targetUser.getUsername().equals(callerUsername)
                && !isAdmin(callerUsername)) {
            throw new AccessDeniedException("You are not allowed to modify this user's profile");
        }
    }

    private boolean isAdmin(String username) {
        return userRepository.findByUsername(username)
                .map(u -> u.getRole() == Role.ADMIN)
                .orElse(false);
    }

    private UserProfileDto toDto(UserEntity user, UserProfile profile) {
        return UserProfileDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .bio(profile.getBio())
                .phone(profile.getPhone())
                .location(profile.getLocation())
                .avatarUrl(profile.getAvatarUrl())
                .memberSince(user.getCreatedAt())
                .build();
    }
}
