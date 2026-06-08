package com.tpdev.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdDeletedEvent {
    private Long adId;
    private Long userId;
    private String title;
}
