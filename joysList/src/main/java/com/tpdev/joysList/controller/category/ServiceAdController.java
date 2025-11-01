package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.ServiceAd;
import com.tpdev.joysList.entity.enums.ServiceType;
import com.tpdev.joysList.service.category.ServiceAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads/services")
public class ServiceAdController extends BaseAdController<ServiceAd> {
    private final ServiceAdService serviceAdService;

    public ServiceAdController(ServiceAdService serviceAdService) {
        super(serviceAdService);
        this.serviceAdService = serviceAdService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ServiceAd> createAd(@RequestBody ServiceAd serviceAd) {
        return new ResponseEntity<>(serviceAdService.createAd(serviceAd), HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ServiceAd>> filter(@RequestParam(required = false)ServiceType serviceType) {
        List<ServiceAd> ads = serviceAdService.filter(serviceType);
        return ResponseEntity.ok(ads);
    }
}
