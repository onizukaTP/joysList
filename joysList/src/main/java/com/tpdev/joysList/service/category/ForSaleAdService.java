package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ForSaleAd;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
import com.tpdev.joysList.repo.category.ForSaleAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.ForSaleAdSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForSaleAdService extends BaseAdService<ForSaleAd> {
    private final ForSaleAdRepository repository;

    @Autowired
    public ForSaleAdService(ForSaleAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public ForSaleAd createAd(ForSaleAd forSaleAd) {
        repository.save(forSaleAd);
        return forSaleAd;
    }

    public List<ForSaleAd> filter(
            SoldBy soldBy,
            Condition condition
    ) {
        List<Specification<ForSaleAd>> specs = new ArrayList<>();

        if (soldBy != null) specs.add(ForSaleAdSpecifications.soldBy(soldBy));
        if (condition != null) specs.add(ForSaleAdSpecifications.condition(condition));

        Specification<ForSaleAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return repository.findAll(finalSpec);
    }
}
