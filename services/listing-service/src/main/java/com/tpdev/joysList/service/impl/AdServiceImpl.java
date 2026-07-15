package com.tpdev.joysList.service.impl;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    @Override
    public void createAd(Ad ad) {
        adRepository.save(ad);
    }

    @Override
    public List<Ad> search(String title) {
        return adRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Override
    public Ad updateAd(Long id, Ad updatedAd) {
        return adRepository.findById(id)
                .map(existingAd -> {
                    existingAd.setTitle(updatedAd.getTitle());
                    existingAd.setDescription(updatedAd.getDescription());
                    existingAd.setPrice(updatedAd.getPrice());
                    return adRepository.save(existingAd);
                })
                .orElseThrow(() -> new RuntimeException("Ad not found with id: " + id));
    }

    @Override
    public void deleteAd(Long id) {
        if (!adRepository.existsById(id)) {
            throw new RuntimeException("Ad not found with id: " + id);
        }
        adRepository.deleteById(id);
    }
}
