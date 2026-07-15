package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.EventAd;
import com.tpdev.joysList.entity.enums.EventType;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface EventAdService extends IBaseAdService<EventAd> {

    List<EventAd> filter(EventType eventType, List<EventType> eventTypes);
}
