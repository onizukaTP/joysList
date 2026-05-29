package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.category.GigsAd;
import com.tpdev.joysList.entity.enums.Gigs;
import com.tpdev.joysList.entity.enums.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

public class GigsAdSpecifications {
    public static Specification<GigsAd> gigs(Gigs gigs) {
        return (root, query, criteriaBuilder) ->
                gigs == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("gigs"), gigs);
    }

    public static Specification<GigsAd> paymentStatus(PaymentStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() :
                        cb.equal(root.get("status"), status);
    }
}
