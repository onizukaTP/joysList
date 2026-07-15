package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.ForSaleAd;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
import com.tpdev.joysList.service.category.ForSaleAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ADS_FOR_SALE)
public class ForSaleAdController extends BaseAdController<ForSaleAd> {
    private final ForSaleAdService service;

    @Autowired
    public ForSaleAdController(ForSaleAdService service) {
        super(service);
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<ForSaleAd> createAd(@RequestBody ForSaleAd forSaleAd) {
        service.createAd(forSaleAd);
        return new ResponseEntity<>(forSaleAd, HttpStatus.CREATED);
    }

    @GetMapping(ApiConstants.FILTER)
    public ResponseEntity<List<ForSaleAd>> filter(
            @RequestParam(required = false)SoldBy soldBy,
            @RequestParam(required = false)Condition condition
            ) {
        List<ForSaleAd> filteredList = service.filter(
                soldBy, condition
        );
        return ResponseEntity.ok(filteredList);
    }
}
