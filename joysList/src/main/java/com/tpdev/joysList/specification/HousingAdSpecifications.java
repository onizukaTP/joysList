package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.HousingAd;
import com.tpdev.joysList.entity.HousingType;
import org.springframework.data.jpa.domain.Specification;

public class HousingAdSpecifications {
    public static Specification<HousingAd> hasType(HousingType type) {
        return (root, query, cb) ->
                type == null ? cb.conjunction() :
                        cb.equal(root.get("type"), type);
    }

    public static Specification<HousingAd> minBeds(Byte minBeds) {
        return (root, query, cb) ->
                minBeds == null ? cb.conjunction() :
                        cb.greaterThanOrEqualTo(root.get("numberOfBeds"), minBeds);
    }

    public static Specification<HousingAd> minBaths(Byte minBaths) {
        return (root, query, cb) ->
                minBaths == null ? cb.conjunction() :
                        cb.greaterThanOrEqualTo(root.get("numberOfBathrooms"), minBaths);
    }

    public static Specification<HousingAd> furnished(Boolean furnished) {
        return (root, query, cb) ->
                furnished == null ? cb.conjunction() :
                        cb.equal(root.get("furnished"), furnished);
    }

    public static Specification<HousingAd>  catsOk(Boolean catsOk) {
        return (root, query, cb) ->
                catsOk == null ? cb.conjunction() :
                        cb.equal(root.get("catsOk"), catsOk);
    }

    public static Specification<HousingAd> dogsOk(Boolean dogsOk) {
        return (root, query, cb) ->
                dogsOk == null ? cb.conjunction() :
                        cb.equal(root.get("dogsOk"), dogsOk);
    }
}
