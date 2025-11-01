package com.tpdev.joysList.service;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.mapper.AdMapper;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.service.category.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdFacadeService {

    private final HousingAdService housingAdService;
    private final ForSaleAdService forSaleAdService;
    private final EventAdService eventAdService;
    private final GigsAdService gigsAdService;
    private final JobAdService jobAdService;
    private final AdMapper mapper;
    private final SubcategoryRepository subcategoryRepository;
    private final AdRepository repository;

    public Ad createAd(AdRequestDto dto) {
        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subcategory ID"));

        return switch (dto.getAdType().toUpperCase()) {
            case "HOUSING" -> housingAdService.createAd(mapper.toHousingAd(dto, subcategory));
            case "FOR_SALE" -> forSaleAdService.createAd(mapper.toForSaleAd(dto, subcategory));
            case "EVENT" -> eventAdService.createAd(mapper.toEventAd(dto, subcategory));
            case "GIGS" -> gigsAdService.createAd(mapper.toGigsAd(dto, subcategory));
            case "JOBS" -> jobAdService.createAd(mapper.toJobAd(dto, subcategory));
            default -> throw new IllegalArgumentException("Unknown ad type: " + dto.getAdType());
        };
    }

    public List<Ad> createMultipleAds(List<AdRequestDto> dto) {
        List<Ad> adList = new ArrayList<>();
        for (AdRequestDto ad : dto) {
            adList.add(createAd(ad));
        }
        return adList;
    }

    public List<Ad> search(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<Ad> getAll() {
        return repository.findAll();
    }
}


