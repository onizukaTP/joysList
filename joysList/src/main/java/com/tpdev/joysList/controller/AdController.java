package com.tpdev.joysList.controller;

import com.tpdev.joysList.dto.AdDTO;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.service.AdMapper;
import com.tpdev.joysList.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @PostMapping
    public ResponseEntity<Ad> createdAt(@RequestBody Ad ad) {
        Ad saved = adService.createAd(ad);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ad>> getAds(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double low,
            @RequestParam(required = false) Double high
    ) {
        List<Ad> ads = adService.searchAds(category, keyword, low, high);
        if (ads.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads =adService.getAllAds();
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AdDTO>> searchAds(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category
    ) {
        List<AdDTO> adDTOs = adService.searchAds(keyword)
                .stream()
                .map(AdMapper::toDTO)
                .toList();
        return ResponseEntity.ok(adDTOs);
    }

    @GetMapping("/priceRange")
    public ResponseEntity<?> getAdByPriceRange(
            @RequestParam(required = false) Double lowRange,
            @RequestParam(required = false) Double highRange
    ) {
        List<Ad> ads = adService.getAdBetweenPriceRange(lowRange, highRange);

        if (highRange == null) {
            List<Ad> adList = adService.getAdFromPriceRange(lowRange);
            return new ResponseEntity<>(adList, HttpStatus.OK);
        }

        if (lowRange != null && highRange < lowRange) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Are you dumb? highRange should be greater than smallRange.");
        }

        if (ads.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("No ads available within that range.");
        }

        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDTO> getAd(@PathVariable Long id) {
        return adService.getAd(id)
                .map(AdMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAt(
            @PathVariable Long id,
            @RequestBody Ad ad
    ) {
        Ad updatedAd = adService.updateAd(id, ad);
        return ResponseEntity.ok(updatedAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAd (@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.ok("Ad successfully deleted.");
    }
}
