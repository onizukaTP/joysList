package com.tpdev.joysList.repo;

import com.tpdev.joysList.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByTitleContainingIgnoreCase(String keyword);
    List<Ad> findByPriceBetweenOrderByPriceAsc(Double lowRange, Double highRange);
    List<Ad> findByPriceGreaterThanEqualOrderByPriceAsc(Double lowRange);
    List<Ad> findByCategory_NameIgnoreCase(String categoryName);
    List<Ad> findByCategory_NameIgnoreCaseAndTitleContainingIgnoreCase(String category, String keyword);


    @Query("""
        SELECT a FROM Ad a
        WHERE (:category IS NULL OR LOWER(a.category.name) = LOWER(:category))
        AND (:lowPrice IS NULL OR a.price >= :lowPrice)
        AND (:highPrice IS NULL OR a.price <= :highPrice)
        AND (:keyword IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
        """)
    List<Ad> searchAds(
            @Param("category") String category,
            @Param("lowPrice") Double lowPrice,
            @Param("highPrice") Double highPrice,
            @Param("keyword") String keyword
    );

}
