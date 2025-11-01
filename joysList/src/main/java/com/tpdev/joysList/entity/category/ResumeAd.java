package com.tpdev.joysList.entity.category;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.EducationCompleted;
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
public class ResumeAd extends Ad {
    private Boolean availableMornings;
    private Boolean availableAfternoons;
    private Boolean availableEvenings;
    private Boolean availableOvernights;
    private Boolean availableWeekdays;
    private Boolean availableWeekends;

    @Enumerated(EnumType.STRING)
    private EducationCompleted educationCompleted;
}
