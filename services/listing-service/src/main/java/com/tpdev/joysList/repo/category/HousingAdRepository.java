package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.category.HousingAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingAdRepository extends JpaRepository<HousingAd, Long>, JpaSpecificationExecutor<HousingAd> {
}
