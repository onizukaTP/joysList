package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.category.EventAd;
import com.tpdev.joysList.entity.enums.EventType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EventAdSpecifications {
    public static Specification<EventAd> hasEventType (EventType eventType) {
        return (root, query, cb) -> {
            if (eventType == null) return cb.conjunction();
            Join<EventAd, EventType> join = root.join("eventTypes", JoinType.INNER);
            return cb.equal(join, eventType);
        };
    }

    public static Specification<EventAd> hasAnyOfEventTypes (List<EventType> eventTypes) {
        return (root, query, criteriaBuilder) -> {
          if (eventTypes.isEmpty()) return criteriaBuilder.conjunction();
          Join<EventAd, EventType> join = root.join("eventTypes", JoinType.INNER);
          return join.in(eventTypes);
        };
    }
}
