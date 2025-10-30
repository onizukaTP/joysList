package com.tpdev.joysList.controller;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.mapper.AdMapper;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.service.AdFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdFacadeService adFacadeService;

    @PostMapping
    public ResponseEntity<String> createAd(@RequestBody AdRequestDto dto) {
        Ad createdAd = adFacadeService.createAd(dto);
        return new ResponseEntity<>("Ad created successfully as " + dto.getAdType(), HttpStatus.CREATED);
    }
}

