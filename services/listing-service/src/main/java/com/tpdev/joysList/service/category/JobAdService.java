package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.JobAd;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.entity.enums.JobType;
import com.tpdev.joysList.repo.category.JobAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.JobAdSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobAdService extends BaseAdService<JobAd> {

    private final JobAdRepository jobAdRepository;

    protected JobAdService(JobAdRepository jobAdRepository) {
        super(jobAdRepository);
        this.jobAdRepository = jobAdRepository;
    }

    @Override
    public JobAd createAd(JobAd jobAd) {
        jobAdRepository.save(jobAd);
        return jobAd;
    }

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
        if (!jobTypes.isEmpty() && jobTypes != null) specifications.add(JobAdSpecifications.hasAnyOfJobTypes(jobTypes));

        Specification<JobAd> finalSpec = specifications.stream()
                .reduce(Specification::and).orElse(null);

        return jobAdRepository.findAll(finalSpec);
    }
}
