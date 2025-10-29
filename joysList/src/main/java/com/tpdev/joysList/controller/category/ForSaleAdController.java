package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.entity.ForSaleAd;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
import com.tpdev.joysList.service.category.ForSaleAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads/forsale")
@RequiredArgsConstructor
public class ForSaleAdController {
    private final ForSaleAdService service;

    @GetMapping("/filter")
    public ResponseEntity<List<ForSaleAd>> filter(
            @RequestParam(required = false)SoldBy soldBy,
            @RequestParam(required = false)Condition condition,
            @RequestParam(required = false, defaultValue = "createdAt")String sortBy,
            @RequestParam(required = false, defaultValue = "desc")String sortOrder
            ) {
        List<ForSaleAd> filteredList = service.filter(
                soldBy, condition, sortBy, sortOrder
        );
        return ResponseEntity.ok(filteredList);
    }
}
