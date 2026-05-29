package com.tpdev.joysList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreatedEvent {
    private Long adId;
    private Long userId;
    private String title;
    private String category;
}
