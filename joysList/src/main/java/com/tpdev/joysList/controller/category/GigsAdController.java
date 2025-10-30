package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.GigsAd;
import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
import com.tpdev.joysList.service.category.GigsAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads/gigs")
public class GigsAdController extends BaseAdController<GigsAd> {
    private final GigsAdService service;

    @Autowired
    public GigsAdController(GigsAdService service) {
        super(service);
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GigsAd> createAd(@RequestBody GigsAd gigsAd) {
        service.createAd(gigsAd);
        return new ResponseEntity<>(gigsAd, HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GigsAd>> filter(
            @RequestParam(required = false)Gigs gigs,
            @RequestParam(required = false)PaymentStatus status
            ) {
        List<GigsAd> adList = service.filter(gigs, status);
        return ResponseEntity.ok(adList);
    }
}
