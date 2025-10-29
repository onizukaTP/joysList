package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.repo.category.HousingAdRepository;
import com.tpdev.joysList.specification.HousingAdSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HousingAdService {
    private final HousingAdRepository repository;

    public List<HousingAd> filter (
            HousingType type,
            Byte minBeds,
            Byte minBaths,
            Boolean furnished,
            Boolean catsOk,
            Boolean dogsOk,
            String sortBy,
            String sortOrder
    ) {
        List<Specification<HousingAd>> specs = new ArrayList<>();

        if (type != null) specs.add(HousingAdSpecifications.hasType(type));
        if (minBeds != null) specs.add(HousingAdSpecifications.minBeds(minBeds));
        if (minBaths != null) specs.add(HousingAdSpecifications.minBaths(minBaths));
        if (furnished != null) specs.add(HousingAdSpecifications.furnished(furnished));
        if (catsOk != null) specs.add(HousingAdSpecifications.catsOk(catsOk));
        if (dogsOk != null) specs.add(HousingAdSpecifications.dogsOk(dogsOk));

        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Specification<HousingAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return repository.findAll(finalSpec, sort);
    }
}
