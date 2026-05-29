package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.category.ResumeAd;
import com.tpdev.joysList.entity.enums.EducationCompleted;
import org.springframework.data.jpa.domain.Specification;

public class ResumeAdSpecifications {
    public static Specification<ResumeAd> availableMornings(Boolean availableMornings) {
        return (root, query, criteriaBuilder) ->
                availableMornings == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("availableMornings"), availableMornings);
    }

    public static Specification<ResumeAd> availableAfternoons(Boolean availableAfternoons) {
        return (root, query, criteriaBuilder) ->
                availableAfternoons == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("availableAfternoons"), availableAfternoons);
    }

    public static Specification<ResumeAd> availableEvenings(Boolean availableEvenings) {
        return (root, query, criteriaBuilder) ->
                availableEvenings == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("availableEvenings"), availableEvenings);
    }

    public static Specification<ResumeAd> availableOvernights(Boolean availableOvernights) {
        return (root, query, criteriaBuilder) ->
                availableOvernights == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("availableOvernights"), availableOvernights);
    }

    public static Specification<ResumeAd> availableWeekdays(Boolean availableWeekdays) {
        return (root, query, criteriaBuilder) ->
                availableWeekdays == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("availableWeekdays"), availableWeekdays);
    }

    public static Specification<ResumeAd> availableWeekends(Boolean availableWeekends) {
        return (root, query, criteriaBuilder) ->
                availableWeekends == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("availableWeekends"), availableWeekends);
    }

    public static Specification<ResumeAd> hasEducationCompleted(EducationCompleted educationCompleted) {
        return (root, query, criteriaBuilder) ->
                educationCompleted == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("educationCompleted"), educationCompleted);
    }
}
