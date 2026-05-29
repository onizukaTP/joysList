package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.category.ServiceAd;
import com.tpdev.joysList.entity.enums.ServiceType;
import org.springframework.data.jpa.domain.Specification;

public class ServiceAdSpecifications {
    public static Specification<ServiceAd> hasServiceType(ServiceType serviceType) {
        return (root, query, criteriaBuilder) ->
                serviceType == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("serviceType"), serviceType);
    }
}
