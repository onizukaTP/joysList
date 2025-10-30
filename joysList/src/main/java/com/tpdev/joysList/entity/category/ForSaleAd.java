package com.tpdev.joysList.entity.category;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
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
public class ForSaleAd extends Ad {

    @Enumerated(EnumType.STRING)
    private SoldBy soldBy;

    @Enumerated(EnumType.STRING)
    private Condition condition;
}
