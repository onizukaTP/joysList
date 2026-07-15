package com.tpdev.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdatedEvent {
    private Long userId;
    private String avatarUrl;
    private String location;
}
