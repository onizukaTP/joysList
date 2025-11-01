package com.tpdev.joysList.entity.category;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAd extends Ad {
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
}
