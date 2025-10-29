package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.ForSaleAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForSaleAdRepository extends JpaRepository<ForSaleAd, Long>, JpaSpecificationExecutor<ForSaleAd> {
}
