package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ResumeAd;
import com.tpdev.joysList.entity.enums.EducationCompleted;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface ResumeAdService extends IBaseAdService<ResumeAd> {

    List<ResumeAd> filter(
            Boolean availableMornings,
            Boolean availableAfternoons,
            Boolean availableEvenings,
            Boolean availableOvernights,
            Boolean availableWeekdays,
            Boolean availableWeekends,
            EducationCompleted educationCompleted
    );
}
