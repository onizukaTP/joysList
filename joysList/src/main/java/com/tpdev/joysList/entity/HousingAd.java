package com.tpdev.joysList.entity;

import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.entity.enums.Laundry;
import com.tpdev.joysList.entity.enums.Parking;
import com.tpdev.joysList.entity.enums.RentPeriod;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousingAd extends Ad{
    private Byte numberOfBeds;
    private Byte numberOfBathrooms;
    private Boolean catsOk;
    private Boolean dogsOk;
    private Boolean furnished;
    private Integer sqft;
    private Boolean privateRoom;
    private Boolean privateBath;
    private Boolean noSmoking;
    private Boolean wheelChairAccessible;
    private Boolean airConditioning;
    private Boolean evCharging;
    private Boolean noBrokerFee;
    private Boolean noApplicationFee;

    @Enumerated(EnumType.STRING)
    private RentPeriod rentPeriod;

    @Enumerated(EnumType.STRING)
    private Laundry laundry;

    @Enumerated(EnumType.STRING)
    private Parking parking;

    @Enumerated(EnumType.STRING)
    private HousingType type;
}
