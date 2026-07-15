package com.tpdev.joysList.service.category.impl;

import com.tpdev.joysList.entity.category.GigsAd;
import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
import com.tpdev.joysList.repo.category.GigsAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.service.category.GigsAdService;
import com.tpdev.joysList.specification.GigsAdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GigsAdServiceImpl extends BaseAdService<GigsAd> implements GigsAdService {

    private final GigsAdRepository repository;

    @Autowired
    public GigsAdServiceImpl(GigsAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public GigsAd createAd(GigsAd gigsAd) {
        repository.save(gigsAd);
        return gigsAd;
    }

    @Override
    public Optional<GigsAd> getAd(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<GigsAd> getAllAds() {
        return repository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<GigsAd> filter(Gigs gigs, PaymentStatus status) {
        List<Specification<GigsAd>> spec = new ArrayList<>();

        if (gigs != null) spec.add(GigsAdSpecifications.gigs(gigs));
        if (status != null) spec.add(GigsAdSpecifications.paymentStatus(status));

        Specification<GigsAd> finalSpec = spec.stream()
                .reduce(Specification::and).orElse(null);
        return repository.findAll(finalSpec);
    }
}
