package com.tpdev.joysList.controller.category;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.controller.BaseAdController;
import com.tpdev.joysList.entity.category.JobAd;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.entity.enums.JobType;
import com.tpdev.joysList.service.category.JobAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ADS_JOBS)
public class JobAdController extends BaseAdController<JobAd> {

    private final JobAdService jobAdService;

    @Autowired
    protected JobAdController(JobAdService jobAdService) {
        super(jobAdService);
        this.jobAdService = jobAdService;
    }

    @PostMapping
    @Override
    public ResponseEntity<JobAd> createAd(@RequestBody JobAd jobAd) {
        jobAdService.createAd(jobAd);
        return new ResponseEntity<>(jobAd, HttpStatus.CREATED);
    }

    @GetMapping(ApiConstants.FILTER)
    public ResponseEntity<List<JobAd>> filter(
            @RequestParam(required = false)Boolean nonProfitOrganization,
            @RequestParam(required = false)Boolean internship,
            @RequestParam(required = false)Boolean teleCommutingOk,
            @RequestParam(required = false)EmploymentType employmentType,
            @RequestParam(required = false)JobType jobType,
            @RequestParam(required = false)List<JobType> jobTypes
            ) {
        List<JobAd> adList = jobAdService.filter(nonProfitOrganization, internship,
                teleCommutingOk, employmentType, jobType, jobTypes);
        return ResponseEntity.ok(adList);
    }
}
