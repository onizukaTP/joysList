package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.entity.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.service.category.HousingAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads/housing")
@RequiredArgsConstructor
public class HousingAdController {
    private final HousingAdService housingAdService;

    @GetMapping("/filter")
    public ResponseEntity<List<HousingAd>> filterHousing (
            @RequestParam(required = false) HousingType type,
            @RequestParam(required = false) Byte minBeds,
            @RequestParam(required = false) Byte minBaths,
            @RequestParam(required = false) Boolean furnished,
            @RequestParam(required = false) Boolean catsOk,
            @RequestParam(required = false) Boolean dogsOk,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
            ) {
        List<HousingAd> filteredList = housingAdService.filter(
                type, minBeds, minBaths, furnished, catsOk, dogsOk, sortBy, sortOrder
        );

        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }
}
