package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ServiceAd;
import com.tpdev.joysList.entity.enums.ServiceType;
import com.tpdev.joysList.repo.category.ServiceAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.ServiceAdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceAdService extends BaseAdService<ServiceAd> {
    private final ServiceAdRepository serviceAdRepository;

    @Autowired
    public ServiceAdService(ServiceAdRepository serviceAdRepository) {
        super(serviceAdRepository);
        this.serviceAdRepository = serviceAdRepository;
    }

    @Override
    public ServiceAd createAd(ServiceAd serviceAd) {
        serviceAdRepository.save(serviceAd);
        return serviceAd;
    }

    public List<ServiceAd> filter(ServiceType serviceType) {
        List<Specification<ServiceAd>> specifications = new ArrayList<>();
        if (serviceType != null) specifications.add(ServiceAdSpecifications.hasServiceType(serviceType));

        Specification<ServiceAd> finalSpec = specifications.stream()
                .reduce(Specification::and).orElse(null);
        return serviceAdRepository.findAll(finalSpec);
    }
}
