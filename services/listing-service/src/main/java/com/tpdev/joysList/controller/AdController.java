package com.tpdev.joysList.controller;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.dto.AdResponse;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.AdType;
import com.tpdev.joysList.service.AdFacadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<AdResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean isFree,
            @RequestParam(required = false) Boolean deliveryAvailable,
            @RequestParam(required = false) Boolean postedToday,
            @RequestParam(required = false) AdType category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("GET /ads/search called");

        Pageable pageable = PageRequest.of(
                page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending()
        );

        Page<AdResponse> results = adFacadeService.search(
                keyword, location, minPrice, maxPrice,
                isFree, deliveryAvailable, postedToday, category, pageable
        );

        return ResponseEntity.ok(results);
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

