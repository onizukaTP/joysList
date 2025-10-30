package com.tpdev.joysList.repo.category;

import com.tpdev.joysList.entity.category.EventAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventAdRepository extends JpaRepository<EventAd, Long>, JpaSpecificationExecutor<EventAd> {
}
