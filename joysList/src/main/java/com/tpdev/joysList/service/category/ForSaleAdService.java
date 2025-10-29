package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.ForSaleAd;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
import com.tpdev.joysList.repo.category.ForSaleAdRepository;
import com.tpdev.joysList.specification.ForSaleAdSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForSaleAdService {
    private final ForSaleAdRepository repository;

    public List<ForSaleAd> filter(
            SoldBy soldBy,
            Condition condition,
            String sortBy,
            String sortOrder
    ) {
        List<Specification<ForSaleAd>> specs = new ArrayList<>();

        if (soldBy != null) specs.add(ForSaleAdSpecifications.soldBy(soldBy));
        if (condition != null) specs.add(ForSaleAdSpecifications.condition(condition));

        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Specification<ForSaleAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return repository.findAll(finalSpec, sort);
    }
}
