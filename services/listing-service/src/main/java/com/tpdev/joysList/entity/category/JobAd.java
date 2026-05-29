package com.tpdev.joysList.entity.category;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.entity.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class JobAd extends Ad {
    private Boolean nonProfitOrganization;
    private Boolean internship;
    private Boolean telecommutingOk;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @ElementCollection(targetClass = JobType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "job_ad_job_types",
            joinColumns = @JoinColumn(name = "job_ad_id")
    )
    @Column(name = "job_type")
    private List<JobType> jobTypes;
}
