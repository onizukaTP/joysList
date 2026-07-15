package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Ad;

import java.util.List;

public interface AdService {

    void createAd(Ad ad);

    List<Ad> search(String title);

    List<Ad> getAllAds();

    Ad updateAd(Long id, Ad updatedAd);

    void deleteAd(Long id);
}
