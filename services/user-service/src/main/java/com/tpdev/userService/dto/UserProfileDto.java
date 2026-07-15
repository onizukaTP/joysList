package com.tpdev.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long userId;
    private String username;
    private String email;
    private String role;
    private String bio;
    private String phone;
    private String location;
    private String avatarUrl;
    private LocalDateTime memberSince;
}
