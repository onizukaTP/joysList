package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.AdType;
import org.springframework.data.jpa.domain.Specification;

public class AdSpecification {

    public static Specification<Ad> keywordMatches(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return cb.conjunction();
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Ad> locationMatches(String location) {
        return (root, query, cb) -> {
            if (location == null || location.isBlank()) return cb.conjunction();
            String pattern = "%" + location.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("location")), pattern);
        };
    }

    public static Specification<Ad> minPrice(Double minPrice) {
        return (root, query, cb) ->
                minPrice == null ? cb.conjunction() :
                        cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Ad> maxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? cb.conjunction() :
                        cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Ad> isFree(Boolean isFree) {
        return (root, query, cb) ->
                isFree == null ? cb.conjunction() :
                        cb.equal(root.get("isFree"), isFree);
    }

    public static Specification<Ad> hasDelivery(Boolean deliveryAvailable) {
        return (root, query, cb) ->
                deliveryAvailable == null ? cb.conjunction() :
                        cb.equal(root.get("deliveryAvailable"), deliveryAvailable);
    }

    public static Specification<Ad> inCategory(AdType category) {
        return (root, query, cb) -> {
            if (category == null) return cb.conjunction();
            // subcategory → category → name
            return cb.equal(
                    root.join("subcategory").join("category").get("name"),
                    category.name()
            );
        };
    }

    public static Specification<Ad> postedToday(Boolean postedToday) {
        return (root, query, cb) ->
                postedToday == null ? cb.conjunction() :
                        cb.equal(root.get("postedToday"), postedToday);
    }
}