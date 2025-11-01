package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.entity.enums.Laundry;
import com.tpdev.joysList.entity.enums.Parking;
import com.tpdev.joysList.entity.enums.RentPeriod;
import com.tpdev.joysList.service.category.HousingAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads/housing")
public class HousingAdController extends BaseAdController<HousingAd> {
    private final HousingAdService housingAdService;

    @Autowired
    public HousingAdController(HousingAdService housingAdService) {
        super(housingAdService);
        this.housingAdService = housingAdService;
    }

    @PostMapping
    @Override
    public ResponseEntity<HousingAd> createAd(@RequestBody HousingAd housingAd) {
        housingAdService.createAd(housingAd);
        return new ResponseEntity<>(housingAd, HttpStatus.CREATED);
    }

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
            @RequestParam(required = false) Parking parking
            ) {
        List<HousingAd> filteredList = housingAdService.filter(
                type, minBeds, minBaths, furnished, catsOk, dogsOk, sqft, privateRoom,
                privateBath, noSmoking, wheelChairAccessible, airConditioning, evCharging,
                noBrokerFee, noApplicationFee, rentPeriod, laundry, parking
        );

        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }
}
