package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.JobAd;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.entity.enums.JobType;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface JobAdService extends IBaseAdService<JobAd> {

    List<JobAd> filter(
            Boolean nonProfitOrganization,
            Boolean internship,
            Boolean teleCommutingOk,
            EmploymentType employmentType,
            JobType jobType,
            List<JobType> jobTypes
    );
}
