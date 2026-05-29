package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.category.EventAd;
import com.tpdev.joysList.entity.category.JobAd;
import com.tpdev.joysList.entity.enums.EmploymentType;
import com.tpdev.joysList.entity.enums.JobType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class JobAdSpecifications {
    public static Specification<JobAd> nonProfitOrganization(Boolean nonProfitOrganization) {
        return (root, query, criteriaBuilder) ->
                nonProfitOrganization == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("nonProfitOrganization"), nonProfitOrganization);
    }

    public static Specification<JobAd> internship(Boolean internship) {
        return (root, query, criteriaBuilder) ->
                internship == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("internship"), internship);
    }

    public static Specification<JobAd> teleCommutingOk(Boolean teleCommutingOk) {
        return (root, query, criteriaBuilder) ->
                teleCommutingOk == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("telecommutingOk"), teleCommutingOk);
    }

    public static Specification<JobAd> hasEmploymentType(EmploymentType employmentType) {
        return (root, query, criteriaBuilder) ->
                employmentType == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("employmentType"), employmentType);
    }

    public static Specification<JobAd> hasJobType(JobType jobType) {
        return (root, query, criteriaBuilder) ->
        {
            if (jobType == null) return criteriaBuilder.conjunction();
            Join<JobAd, JobType> join = root.join("jobTypes", JoinType.INNER);
            return join.in(jobType);
        };
    }

    public static Specification<JobAd> hasAnyOfJobTypes (List<JobType> jobTypes) {
        return (root, query, criteriaBuilder) -> {
            if (jobTypes.isEmpty()) return criteriaBuilder.conjunction();
            Join<JobAd, JobType> join = root.join("jobTypes", JoinType.INNER);
            return join.in(jobTypes);
        };
    }
}
