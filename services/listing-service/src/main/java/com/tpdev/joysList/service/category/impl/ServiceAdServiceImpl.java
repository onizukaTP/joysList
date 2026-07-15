package com.tpdev.joysList.service.category.impl;

import com.tpdev.joysList.entity.category.ServiceAd;
import com.tpdev.joysList.entity.enums.ServiceType;
import com.tpdev.joysList.repo.category.ServiceAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.service.category.ServiceAdService;
import com.tpdev.joysList.specification.ServiceAdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceAdServiceImpl extends BaseAdService<ServiceAd> implements ServiceAdService {

    private final ServiceAdRepository serviceAdRepository;

    @Autowired
    public ServiceAdServiceImpl(ServiceAdRepository serviceAdRepository) {
        super(serviceAdRepository);
        this.serviceAdRepository = serviceAdRepository;
    }

    @Override
    public ServiceAd createAd(ServiceAd serviceAd) {
        serviceAdRepository.save(serviceAd);
        return serviceAd;
    }

    @Override
    public Optional<ServiceAd> getAd(Long id) {
        return serviceAdRepository.findById(id);
    }

    @Override
    public List<ServiceAd> getAllAds() {
        return serviceAdRepository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        serviceAdRepository.deleteById(id);
    }

    @Override
    public List<ServiceAd> filter(ServiceType serviceType) {
        List<Specification<ServiceAd>> specifications = new ArrayList<>();
        if (serviceType != null) specifications.add(ServiceAdSpecifications.hasServiceType(serviceType));

        Specification<ServiceAd> finalSpec = specifications.stream()
                .reduce(Specification::and).orElse(null);
        return serviceAdRepository.findAll(finalSpec);
    }
}
