package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.CommunityAd;
import com.tpdev.joysList.entity.enums.CommunityType;
import com.tpdev.joysList.entity.enums.LostAndFound;
import com.tpdev.joysList.service.category.CommunityAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ADS_COMMUNITY)
public class CommunityAdController extends BaseAdController<CommunityAd> {
    private final CommunityAdService service;

    public CommunityAdController(CommunityAdService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<CommunityAd> createAd(@RequestBody CommunityAd communityAd) {
        return new ResponseEntity<>(service.createAd(communityAd), HttpStatus.CREATED);
    }

    @GetMapping(ApiConstants.FILTER)
    public ResponseEntity<List<CommunityAd>> filter(
            @RequestParam(required = false)CommunityType communityType,
            @RequestParam(required = false)LostAndFound lostOrFound
            ) {
        List<CommunityAd> ads = service.filter(communityType, lostOrFound);
        return ResponseEntity.ok(ads);
    }
}
