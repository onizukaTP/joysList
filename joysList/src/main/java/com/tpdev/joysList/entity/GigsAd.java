package com.tpdev.joysList.entity;

import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
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
@AllArgsConstructor
@NoArgsConstructor
public class GigsAd extends Ad{
    @Enumerated(EnumType.STRING)
    private Gigs gigs;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
