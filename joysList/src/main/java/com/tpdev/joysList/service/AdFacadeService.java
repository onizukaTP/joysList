package com.tpdev.joysList.service;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.mapper.AdMapper;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.service.category.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdFacadeService {

    private final HousingAdService housingAdService;
    private final ForSaleAdService forSaleAdService;
    private final EventAdService eventAdService;
    private final GigsAdService gigsAdService;
    private final AdMapper mapper;
    private final SubcategoryRepository subcategoryRepository;

    public Ad createAd(AdRequestDto dto) {
        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subcategory ID"));

        return switch (dto.getAdType().toUpperCase()) {
            case "HOUSING" -> housingAdService.createAd(mapper.toHousingAd(dto, subcategory));
            case "FOR_SALE" -> forSaleAdService.createAd(mapper.toForSaleAd(dto, subcategory));
            case "EVENT" -> eventAdService.createAd(mapper.toEventAd(dto, subcategory));
            case "GIGS" -> gigsAdService.createAd(mapper.toGigsAd(dto, subcategory));
            default -> throw new IllegalArgumentException("Unknown ad type: " + dto.getAdType());
        };
    }
}


