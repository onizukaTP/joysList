package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.ResumeAd;
import com.tpdev.joysList.entity.enums.EducationCompleted;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.service.category.ResumeAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ADS_RESUMES)
public class ResumeAdController extends BaseAdController<ResumeAd> {
    private final ResumeAdService service;

    @Autowired
    public ResumeAdController(ResumeAdService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResumeAd> createAd(@RequestBody ResumeAd resumeAd) {
        return new ResponseEntity<>(resumeAd, HttpStatus.CREATED);
    }

    @GetMapping(ApiConstants.FILTER)
    public ResponseEntity<List<ResumeAd>> filter(
            @RequestParam(required = false)Boolean availableMornings,
            @RequestParam(required = false)Boolean availableAfternoons,
            @RequestParam(required = false)Boolean availableEvenings,
            @RequestParam(required = false)Boolean availableOvernights,
            @RequestParam(required = false)Boolean availableWeekdays,
            @RequestParam(required = false)Boolean availableWeekends,
            @RequestParam(required = false)EducationCompleted educationCompleted
            ) {
        List<ResumeAd> ads = service.filter(
                availableMornings, availableAfternoons, availableEvenings, availableOvernights,
                availableWeekdays, availableWeekends, educationCompleted
        );
        return ResponseEntity.ok(ads);
    }
}
