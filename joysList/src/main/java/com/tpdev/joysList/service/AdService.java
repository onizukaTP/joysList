package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.repo.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;

    public Ad createAd(Ad ad) {
        return adRepository.save(ad);
    }

    public List<Ad> searchAds(String keyword) {
        return adRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Optional<Ad> getAd(Long id) {
        return adRepository.findById(id);
    }

    public List<Ad> getAdBetweenPriceRange(Double lowRange, Double highRange) {
        if (lowRange == null && highRange == null) {
            return adRepository.findAll();
        }

        return adRepository.findByPriceBetweenOrderByPriceAsc(lowRange, highRange);
    }

    public List<Ad> getAdFromPriceRange(Double lowRange) {
        if (lowRange == null) {
            return adRepository.findAll();
        }
        return adRepository.findByPriceGreaterThanEqualOrderByPriceAsc(lowRange);
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }
}
