package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.EventAd;
import com.tpdev.joysList.entity.enums.EventType;
import com.tpdev.joysList.repo.category.EventAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.EventAdSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventAdService extends BaseAdService<EventAd> {
    private final EventAdRepository eventAdRepository;

    public EventAdService(EventAdRepository eventAdRepository) {
        super(eventAdRepository);
        this.eventAdRepository = eventAdRepository;
    }

    public EventAd createAd(EventAd eventAd) {
        eventAdRepository.save(eventAd);
        return eventAd;
    }

    public List<EventAd> filter(EventType eventType, List<EventType> eventTypes) {
        List<Specification<EventAd>> specs = new ArrayList<>();

        if (eventType != null) specs.add(EventAdSpecifications.hasEventType(eventType));
        if (!eventTypes.isEmpty() && eventTypes != null) specs.add(EventAdSpecifications.hasAnyOfEventTypes(eventTypes));

        Specification<EventAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return eventAdRepository.findAll(finalSpec);
    }


}
