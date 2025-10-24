package com.tpdev.joysList.service;

import com.tpdev.joysList.dto.AdDTO;
import com.tpdev.joysList.dto.CategoryDTO;
import com.tpdev.joysList.entity.Ad;

public class AdMapper {

    public static AdDTO toDTO(Ad ad) {
        CategoryDTO categoryDTO = new CategoryDTO(
                ad.getCategory().getId(),
                ad.getCategory().getName(),
                ad.getCategory().getDescription()
        );

        return new AdDTO(
                ad.getId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getPrice(),
                categoryDTO
        );
    }
}
