package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ServiceAd;
import com.tpdev.joysList.entity.enums.ServiceType;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface ServiceAdService extends IBaseAdService<ServiceAd> {

    List<ServiceAd> filter(ServiceType serviceType);
}
