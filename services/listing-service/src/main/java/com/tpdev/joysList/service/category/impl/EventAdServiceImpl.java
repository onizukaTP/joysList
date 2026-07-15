package com.tpdev.joysList.service.category.impl;

import com.tpdev.joysList.entity.category.EventAd;
import com.tpdev.joysList.entity.enums.EventType;
import com.tpdev.joysList.repo.category.EventAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.service.category.EventAdService;
import com.tpdev.joysList.specification.EventAdSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventAdServiceImpl extends BaseAdService<EventAd> implements EventAdService {

    private final EventAdRepository eventAdRepository;

    public EventAdServiceImpl(EventAdRepository eventAdRepository) {
        super(eventAdRepository);
        this.eventAdRepository = eventAdRepository;
    }

    @Override
    public EventAd createAd(EventAd eventAd) {
        eventAdRepository.save(eventAd);
        return eventAd;
    }

    @Override
    public Optional<EventAd> getAd(Long id) {
        return eventAdRepository.findById(id);
    }

    @Override
    public List<EventAd> getAllAds() {
        return eventAdRepository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        eventAdRepository.deleteById(id);
    }

    @Override
    public List<EventAd> filter(EventType eventType, List<EventType> eventTypes) {
        List<Specification<EventAd>> specs = new ArrayList<>();

        if (eventType != null) specs.add(EventAdSpecifications.hasEventType(eventType));
        if (eventTypes != null && !eventTypes.isEmpty()) specs.add(EventAdSpecifications.hasAnyOfEventTypes(eventTypes));

        Specification<EventAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return eventAdRepository.findAll(finalSpec);
    }
}
