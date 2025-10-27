package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.HousingAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HousingAdRepository extends JpaRepository<HousingAd, Long>, JpaSpecificationExecutor<HousingAd> {
}
