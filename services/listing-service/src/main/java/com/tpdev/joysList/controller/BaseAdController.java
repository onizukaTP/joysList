package com.tpdev.joysList.controller;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.service.IBaseAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseAdController<T extends Ad> {

    private final IBaseAdService<T> service;

    protected BaseAdController(IBaseAdService<T> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<T> createAd(@RequestBody T ad) {
        T saved = service.createAd(ad);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getAd(@PathVariable Long id) {
        return service.getAd(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<T>> getAllAds() {
        return ResponseEntity.ok(service.getAllAds());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        service.deleteAd(id);
        return ResponseEntity.noContent().build();
    }
}

