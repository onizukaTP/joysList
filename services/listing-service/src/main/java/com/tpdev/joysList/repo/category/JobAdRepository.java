package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.category.JobAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobAdRepository extends JpaRepository<JobAd, Long>, JpaSpecificationExecutor<JobAd> {
}
