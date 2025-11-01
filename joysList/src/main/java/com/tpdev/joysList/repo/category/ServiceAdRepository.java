package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.category.ServiceAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAdRepository extends JpaRepository<ServiceAd, Long>, JpaSpecificationExecutor<ServiceAd> {
}
