package com.tpdev.joysList.service.category.impl;

import com.tpdev.joysList.entity.category.HousingAd;
import com.tpdev.joysList.entity.enums.HousingType;
import com.tpdev.joysList.entity.enums.Laundry;
import com.tpdev.joysList.entity.enums.Parking;
import com.tpdev.joysList.entity.enums.RentPeriod;
import com.tpdev.joysList.repo.category.HousingAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.service.category.HousingAdService;
import com.tpdev.joysList.specification.HousingAdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HousingAdServiceImpl extends BaseAdService<HousingAd> implements HousingAdService {

    private final HousingAdRepository repository;

    @Autowired
    public HousingAdServiceImpl(HousingAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public HousingAd createAd(HousingAd housingAd) {
        repository.save(housingAd);
        return housingAd;
    }

    @Override
    public Optional<HousingAd> getAd(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<HousingAd> getAllAds() {
        return repository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<HousingAd> filter(
            HousingType type,
            Byte minBeds,
            Byte minBaths,
            Boolean furnished,
            Boolean catsOk,
            Boolean dogsOk,
            Integer sqft,
            Boolean privateRoom,
            Boolean privateBath,
            Boolean noSmoking,
            Boolean wheelChairAccessible,
            Boolean airConditioning,
            Boolean evCharging,
            Boolean noBrokerFee,
            Boolean noApplicationFee,
            RentPeriod rentPeriod,
            Laundry laundry,
            Parking parking
    ) {
        List<Specification<HousingAd>> specs = new ArrayList<>();

        if (type != null) specs.add(HousingAdSpecifications.hasType(type));
        if (minBeds != null) specs.add(HousingAdSpecifications.minBeds(minBeds));
        if (minBaths != null) specs.add(HousingAdSpecifications.minBaths(minBaths));
        if (furnished != null) specs.add(HousingAdSpecifications.furnished(furnished));
        if (catsOk != null) specs.add(HousingAdSpecifications.catsOk(catsOk));
        if (dogsOk != null) specs.add(HousingAdSpecifications.dogsOk(dogsOk));
        if (sqft != null) specs.add(HousingAdSpecifications.sqft(sqft));
        if (privateRoom != null) specs.add(HousingAdSpecifications.privateRoom(privateRoom));
        if (privateBath != null) specs.add(HousingAdSpecifications.privateBath(privateBath));
        if (noSmoking != null) specs.add(HousingAdSpecifications.noSmoking(noSmoking));
        if (wheelChairAccessible != null) specs.add(HousingAdSpecifications.wheelChairAccessible(wheelChairAccessible));
        if (airConditioning != null) specs.add(HousingAdSpecifications.airConditioning(airConditioning));
        if (evCharging != null) specs.add(HousingAdSpecifications.evCharging(evCharging));
        if (noBrokerFee != null) specs.add(HousingAdSpecifications.noBrokerFee(noBrokerFee));
        if (noApplicationFee != null) specs.add(HousingAdSpecifications.noApplicationFee(noApplicationFee));
        if (rentPeriod != null) specs.add(HousingAdSpecifications.rentPeriod(rentPeriod));
        if (laundry != null) specs.add(HousingAdSpecifications.laundry(laundry));
        if (parking != null) specs.add(HousingAdSpecifications.parking(parking));

        Specification<HousingAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);

        return repository.findAll(finalSpec);
    }
}
