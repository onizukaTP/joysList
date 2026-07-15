package com.tpdev.joysList.service.category.impl;

import com.tpdev.joysList.entity.category.ForSaleAd;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
import com.tpdev.joysList.repo.category.ForSaleAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.service.category.ForSaleAdService;
import com.tpdev.joysList.specification.ForSaleAdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForSaleAdServiceImpl extends BaseAdService<ForSaleAd> implements ForSaleAdService {

    private final ForSaleAdRepository repository;

    @Autowired
    public ForSaleAdServiceImpl(ForSaleAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public ForSaleAd createAd(ForSaleAd forSaleAd) {
        repository.save(forSaleAd);
        return forSaleAd;
    }

    @Override
    public Optional<ForSaleAd> getAd(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ForSaleAd> getAllAds() {
        return repository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ForSaleAd> filter(SoldBy soldBy, Condition condition) {
        List<Specification<ForSaleAd>> specs = new ArrayList<>();

        if (soldBy != null) specs.add(ForSaleAdSpecifications.soldBy(soldBy));
        if (condition != null) specs.add(ForSaleAdSpecifications.condition(condition));

        Specification<ForSaleAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return repository.findAll(finalSpec);
    }
}
