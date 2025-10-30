package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ForSaleAd;
import com.tpdev.joysList.entity.category.GigsAd;
import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
import com.tpdev.joysList.repo.category.GigsAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.GigsAdSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GigsAdService extends BaseAdService<GigsAd> {
    private final GigsAdRepository repository;

    @Autowired
    public GigsAdService(GigsAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public GigsAd createAd(GigsAd gigsAd) {
        repository.save(gigsAd);
        return gigsAd;
    }

    public List<GigsAd> filter(
            Gigs gigs,
            PaymentStatus status
    ) {
        List<Specification<GigsAd>> spec = new ArrayList<>();

        if (gigs != null) spec.add(GigsAdSpecifications.gigs(gigs));
        if (status != null) spec.add(GigsAdSpecifications.paymentStatus(status));

        Specification<GigsAd> finalSpec = spec.stream()
                .reduce(Specification::and).orElse(null);
        return repository.findAll(finalSpec);
    }
}
