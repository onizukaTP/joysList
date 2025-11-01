package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.category.ResumeAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeAdRepository extends JpaRepository<ResumeAd, Long>, JpaSpecificationExecutor<ResumeAd> {
}
