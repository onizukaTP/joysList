package com.tpdev.joysList.repo;

import com.tpdev.joysList.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByTitleContainingIgnoreCase(String keyword);
    List<Ad> findByPriceBetweenOrderByPriceAsc(Double lowRange, Double highRange);
    List<Ad> findByPriceGreaterThanEqualOrderByPriceAsc(Double lowRange);
}
