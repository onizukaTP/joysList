package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ResumeAd;
import com.tpdev.joysList.entity.enums.EducationCompleted;
import com.tpdev.joysList.repo.category.ResumeAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.ResumeAdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeAdService extends BaseAdService<ResumeAd> {
    private final ResumeAdRepository repository;

    @Autowired
    public ResumeAdService(ResumeAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public ResumeAd createAd(ResumeAd resumeAd) {
        repository.save(resumeAd);
        return resumeAd;
    }

    public List<ResumeAd> filter(
            Boolean availableMornings,
            Boolean availableAfternoons,
            Boolean availableEvenings,
            Boolean availableOvernights,
            Boolean availableWeekdays,
            Boolean availableWeekends,
            EducationCompleted educationCompleted
    ) {
        List<Specification<ResumeAd>> specifications = new ArrayList<>();

        if (availableAfternoons != null) specifications.add(ResumeAdSpecifications.availableAfternoons(availableAfternoons));
        if (availableMornings != null) specifications.add(ResumeAdSpecifications.availableMornings(availableMornings));
        if (availableEvenings != null) specifications.add(ResumeAdSpecifications.availableEvenings(availableEvenings));
        if (availableOvernights != null) specifications.add(ResumeAdSpecifications.availableOvernights(availableOvernights));
        if (availableWeekdays != null) specifications.add(ResumeAdSpecifications.availableWeekdays(availableWeekdays));
        if (availableWeekends != null) specifications.add(ResumeAdSpecifications.availableWeekends(availableWeekends));
        if (educationCompleted != null) specifications.add(ResumeAdSpecifications.hasEducationCompleted(educationCompleted));

        Specification<ResumeAd> finalSpec = specifications.stream()
                .reduce(Specification::and).orElse(null);

        return repository.findAll(finalSpec);
    }
}
