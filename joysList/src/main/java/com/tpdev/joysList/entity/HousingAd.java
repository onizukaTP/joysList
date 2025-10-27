package com.tpdev.joysList.entity;

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
    private byte numberOfBeds;
    private byte numberOfBathrooms;
    private boolean catsOk;
    private boolean dogsOk;
    private boolean furnished;

    @Enumerated(EnumType.STRING)
    private HousingType type;
}
