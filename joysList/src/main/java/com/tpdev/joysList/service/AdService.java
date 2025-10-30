package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.repo.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;

    public Ad createAd(Ad ad) {
        return adRepository.save(ad);
    }

    public List<Ad> search(String title) {
        return adRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

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

    public void deleteAd(Long id) {
        if (!adRepository.existsById(id)) {
            throw new RuntimeException("Ad not found with id: " + id);
        }
        adRepository.deleteById(id);
    }
}
