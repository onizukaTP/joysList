package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.GigsAd;
import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface GigsAdService extends IBaseAdService<GigsAd> {

    List<GigsAd> filter(Gigs gigs, PaymentStatus status);
}
