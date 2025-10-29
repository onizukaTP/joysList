package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.GigsAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GigsAdRepository extends JpaRepository<GigsAd, Long>, JpaSpecificationExecutor<GigsAd> {
}
