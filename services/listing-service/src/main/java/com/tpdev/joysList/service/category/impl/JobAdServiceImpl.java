package com.tpdev.joysList.service.category.impl;

import com.tpdev.joysList.entity.category.JobAd;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.entity.enums.JobType;
import com.tpdev.joysList.repo.category.JobAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.service.category.JobAdService;
import com.tpdev.joysList.specification.JobAdSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobAdServiceImpl extends BaseAdService<JobAd> implements JobAdService {

    private final JobAdRepository jobAdRepository;

    protected JobAdServiceImpl(JobAdRepository jobAdRepository) {
        super(jobAdRepository);
        this.jobAdRepository = jobAdRepository;
    }

    @Override
    public JobAd createAd(JobAd jobAd) {
        jobAdRepository.save(jobAd);
        return jobAd;
    }

    @Override
    public Optional<JobAd> getAd(Long id) {
        return jobAdRepository.findById(id);
    }

    @Override
    public List<JobAd> getAllAds() {
        return jobAdRepository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        jobAdRepository.deleteById(id);
    }

    @Override
    public List<JobAd> filter(
            Boolean nonProfitOrganization,
            Boolean internship,
            Boolean teleCommutingOk,
            EmploymentType employmentType,
            JobType jobType,
            List<JobType> jobTypes
    ) {
        List<Specification<JobAd>> specifications = new ArrayList<>();

        if (nonProfitOrganization != null) specifications.add(JobAdSpecifications.nonProfitOrganization(nonProfitOrganization));
        if (internship != null) specifications.add(JobAdSpecifications.internship(internship));
        if (teleCommutingOk != null) specifications.add(JobAdSpecifications.teleCommutingOk(teleCommutingOk));
        if (employmentType != null) specifications.add(JobAdSpecifications.hasEmploymentType(employmentType));
        if (jobType != null) specifications.add(JobAdSpecifications.hasJobType(jobType));
        if (jobTypes != null && !jobTypes.isEmpty()) specifications.add(JobAdSpecifications.hasAnyOfJobTypes(jobTypes));

        Specification<JobAd> finalSpec = specifications.stream()
                .reduce(Specification::and).orElse(null);

        return jobAdRepository.findAll(finalSpec);
    }
}
