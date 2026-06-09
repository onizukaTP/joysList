package com.tpdev.joysList.dto;

public record AdResponse(
        Long id,
        String title,
        String description,
        Double price,
        String location,
        String subcategoryName,
        Long userId
) {}
