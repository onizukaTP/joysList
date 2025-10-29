package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.entity.enums.Laundry;
import com.tpdev.joysList.entity.enums.Parking;
import com.tpdev.joysList.entity.enums.RentPeriod;
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

    public static Specification<HousingAd> catsOk(Boolean catsOk) {
        return (root, query, cb) ->
                catsOk == null ? cb.conjunction() :
                        cb.equal(root.get("catsOk"), catsOk);
    }

    public static Specification<HousingAd> dogsOk(Boolean dogsOk) {
        return (root, query, cb) ->
                dogsOk == null ? cb.conjunction() :
                        cb.equal(root.get("dogsOk"), dogsOk);
    }

    public static Specification<HousingAd> sqft(Integer sqft) {
        return (root, query, cb) ->
                sqft == null ? cb.conjunction() :
                        cb.greaterThanOrEqualTo(root.get("sqft"), sqft);
    }

    public static Specification<HousingAd> privateRoom(Boolean privateRoom) {
        return (root, query, cb) ->
                privateRoom == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), privateRoom);
    }

    public static Specification<HousingAd> privateBath(Boolean privateBath) {
        return (root, query, cb) ->
                privateBath == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), privateBath);
    }

    public static Specification<HousingAd> noSmoking(Boolean noSmoking) {
        return (root, query, cb) ->
                noSmoking == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), noSmoking);
    }

    public static Specification<HousingAd> wheelChairAccessible(Boolean wheelChairAccessible) {
        return (root, query, cb) ->
                wheelChairAccessible == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), wheelChairAccessible);
    }

    public static Specification<HousingAd> airConditioning(Boolean airConditioning) {
        return (root, query, cb) ->
                airConditioning == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), airConditioning);
    }

    public static Specification<HousingAd> evCharging(Boolean evCharging) {
        return (root, query, cb) ->
                evCharging == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), evCharging);
    }

    public static Specification<HousingAd> noBrokerFee(Boolean noBrokerFee) {
        return (root, query, cb) ->
                noBrokerFee == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), noBrokerFee);
    }

    public static Specification<HousingAd> noApplicationFee(Boolean noApplicationFee) {
        return (root, query, cb) ->
                noApplicationFee == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), noApplicationFee);
    }

    public static Specification<HousingAd> rentPeriod(RentPeriod rentPeriod) {
        return (root, query, cb) ->
                rentPeriod == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), rentPeriod);
    }

    public static Specification<HousingAd> laundry(Laundry laundry) {
        return (root, query, cb) ->
                laundry == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), laundry);
    }

    public static Specification<HousingAd> parking(Parking parking) {
        return (root, query, cb) ->
                parking == null ? cb.conjunction() :
                        cb.equal(root.get("privateRoom"), parking);
    }
}
