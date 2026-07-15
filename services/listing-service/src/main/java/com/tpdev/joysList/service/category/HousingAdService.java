package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.entity.enums.Laundry;
import com.tpdev.joysList.entity.enums.Parking;
import com.tpdev.joysList.entity.enums.RentPeriod;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface HousingAdService extends IBaseAdService<HousingAd> {

    List<HousingAd> filter(
            HousingType type,
            Byte minBeds,
            Byte minBaths,
            Boolean furnished,
            Boolean catsOk,
            Boolean dogsOk,
            Integer sqft,
            Boolean privateRoom,
            Boolean privateBath,
            Boolean noSmoking,
            Boolean wheelChairAccessible,
            Boolean airConditioning,
            Boolean evCharging,
            Boolean noBrokerFee,
            Boolean noApplicationFee,
            RentPeriod rentPeriod,
            Laundry laundry,
            Parking parking
    );
}
