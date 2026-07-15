package com.tpdev.joysList.service;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.dto.AdResponse;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.AdType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdFacadeService {

    Ad createAd(AdRequestDto dto);

    List<Ad> createMultipleAds(List<AdRequestDto> dto);

    Page<AdResponse> search(
            String keyword,
            String location,
            Double minPrice,
            Double maxPrice,
            Boolean isFree,
            Boolean deliveryAvailable,
            Boolean postedToday,
            AdType category,
            Pageable pageable
    );

    List<AdResponse> getAll();

    Ad updateAd(Long id, AdRequestDto dto);

    void deleteAd(Long id);
}