package com.tpdev.joysList.controller;

import com.tpdev.joysList.entity.Ad;
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

    // create an ad
    @PostMapping
    public ResponseEntity<Ad> createdAt(@RequestBody Ad ad) {
        Ad saved = adService.createAd(ad);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    // basic search
    @GetMapping("/search")
    public ResponseEntity<List<Ad>> getAds(@RequestParam(required = false) String title) {
        List<Ad> ads = adService.search(title);
        if (ads.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ads);
    }

    // get everything
    @GetMapping("/all")
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads =adService.getAllAds();
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAt(
            @PathVariable Long id,
            @RequestBody Ad ad
    ) {
        Ad updatedAd = adService.updateAd(id, ad);
        return ResponseEntity.ok(updatedAd);
    }

    // delete obviously
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAd (@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.ok("Ad successfully deleted.");
    }
}
