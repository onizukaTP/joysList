package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Ad;

import java.util.List;
import java.util.Optional;

/**
 * Common contract for all typed ad services.
 * Category-specific service interfaces extend this to inherit
 * the standard CRUD operations, allowing BaseAdController to depend
 * on this abstraction rather than the concrete BaseAdService class.
 */
public interface IBaseAdService<T extends Ad> {

    T createAd(T ad);

    Optional<T> getAd(Long id);

    List<T> getAllAds();

    void deleteAd(Long id);
}
