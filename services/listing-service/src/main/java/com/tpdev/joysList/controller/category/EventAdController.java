package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.EventAd;
import com.tpdev.joysList.entity.enums.EventType;
import com.tpdev.joysList.service.category.EventAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads/events")
public class EventAdController extends BaseAdController<EventAd> {
    private final EventAdService service;

    @Autowired
    public EventAdController(EventAdService service) {
        super(service);
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<EventAd> createAd(@RequestBody EventAd eventAd) {
        service.createAd(eventAd);
        return new ResponseEntity<>(eventAd, HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventAd>> filter(
            @RequestParam(required = false)EventType eventType,
            @RequestParam(required = false)List<EventType> eventTypes
            ) {
        List<EventAd> adList = service.filter(eventType, eventTypes);
        return ResponseEntity.ok(adList);
    }
}
