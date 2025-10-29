package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.entity.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.entity.enums.Laundry;
import com.tpdev.joysList.entity.enums.Parking;
import com.tpdev.joysList.entity.enums.RentPeriod;
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
            @RequestParam(required = false) Integer sqft,
            @RequestParam(required = false) Boolean privateRoom,
            @RequestParam(required = false) Boolean privateBath,
            @RequestParam(required = false) Boolean noSmoking,
            @RequestParam(required = false) Boolean wheelChairAccessible,
            @RequestParam(required = false) Boolean airConditioning,
            @RequestParam(required = false) Boolean evCharging,
            @RequestParam(required = false) Boolean noBrokerFee,
            @RequestParam(required = false) Boolean noApplicationFee,
            @RequestParam(required = false) RentPeriod rentPeriod,
            @RequestParam(required = false) Laundry laundry,
            @RequestParam(required = false) Parking parking,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder
            ) {
        List<HousingAd> filteredList = housingAdService.filter(
                type, minBeds, minBaths, furnished, catsOk, dogsOk, sqft, privateRoom,
                privateBath, noSmoking, wheelChairAccessible, airConditioning, evCharging,
                noBrokerFee, noApplicationFee, rentPeriod, laundry, parking, sortBy, sortOrder
        );

        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }
}
