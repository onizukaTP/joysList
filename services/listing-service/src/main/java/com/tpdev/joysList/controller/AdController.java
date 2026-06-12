package com.tpdev.joysList.controller;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.dto.AdResponse;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.service.AdFacadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
@Slf4j
public class AdController {

    private final AdFacadeService adFacadeService;

    @PostMapping
    public ResponseEntity<String> createAd(@Valid @RequestBody AdRequestDto dto) {
        log.info("POST /ads endpoint called");
        Ad createdAd = adFacadeService.createAd(dto);
        return new ResponseEntity<>("Ad created successfully as " + dto.getAdType(), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<String> createMultipleAds(@RequestBody List<AdRequestDto> dto) {
        log.info("POST /ads/list endpoint called");
        List<Ad> ads = adFacadeService.createMultipleAds(dto);
        return new ResponseEntity<>("Ads created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdResponse>> getAll() {
        log.info("GET /ads endpoint called");
        return ResponseEntity.ok(
                adFacadeService.getAll()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ad>> search(@RequestParam(required = false) String title) {
        log.info("GET /ads/search endpoint called");
        List<Ad> ads = adFacadeService.searchAdByTitle(title);
        if (ads.isEmpty())
            return new ResponseEntity<>(ads, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(ads);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAd(
            @PathVariable Long id,
            @Valid @RequestBody AdRequestDto dto) {
        log.info("PUT /ads/{} endpoint called", id);
        adFacadeService.updateAd(id, dto);
        return ResponseEntity.ok("Ad " + id + " updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAd(@PathVariable Long id) {
        log.info("DELETE /ads/{} endpoint called", id);
        adFacadeService.deleteAd(id);
        return ResponseEntity.ok("Ad " + id + " deleted successfully");
    }
}

