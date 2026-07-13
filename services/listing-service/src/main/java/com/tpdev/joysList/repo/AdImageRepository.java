package com.tpdev.joysList.repo;

import com.tpdev.joysList.entity.AdImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdImageRepository extends JpaRepository<AdImage, Long> {
    List<AdImage> findByAdId(Long adId);
    int countByAdId(Long adId);
}
