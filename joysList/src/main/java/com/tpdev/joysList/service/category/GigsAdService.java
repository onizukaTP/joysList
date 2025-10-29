package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.GigsAd;
import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
import com.tpdev.joysList.repo.category.GigsAdRepository;
import com.tpdev.joysList.specification.GigsAdSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GigsAdService {
    private final GigsAdRepository repository;

    public List<GigsAd> filter(
            Gigs gigs,
            PaymentStatus status,
            String sortBy,
            String sortOrder
    ) {
        List<Specification<GigsAd>> spec = new ArrayList<>();

        if (gigs != null) spec.add(GigsAdSpecifications.gigs(gigs));
        if (status != null) spec.add(GigsAdSpecifications.paymentStatus(status));

        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Specification<GigsAd> finalSpec = spec.stream()
                .reduce(Specification::and).orElse(null);
        return repository.findAll(finalSpec, sort);
    }
}
