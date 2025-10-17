package com.tpdev.joysList.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String url;
    private String thumbnailUrl;
}
