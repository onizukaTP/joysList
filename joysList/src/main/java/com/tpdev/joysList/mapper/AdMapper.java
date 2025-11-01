package com.tpdev.joysList.mapper;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.*;
import com.tpdev.joysList.entity.category.*;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {

    public HousingAd toHousingAd(AdRequestDto dto, Subcategory subcategory) {
        HousingAd ad = new HousingAd();
        setCommonFields(ad, dto, subcategory);
        ad.setNumberOfBeds(dto.getNumberOfBeds());
        ad.setNumberOfBathrooms(dto.getNumberOfBathrooms());
        ad.setCatsOk(dto.getCatsOk());
        ad.setDogsOk(dto.getDogsOk());
        ad.setFurnished(dto.getFurnished());
        ad.setType(dto.getHousingType());
        ad.setLaundry(dto.getLaundry());
        ad.setParking(dto.getParking());
        ad.setRentPeriod(dto.getRentPeriod());
        return ad;
    }

    public ForSaleAd toForSaleAd(AdRequestDto dto, Subcategory subcategory) {
        ForSaleAd ad = new ForSaleAd();
        setCommonFields(ad, dto, subcategory);
        ad.setSoldBy(dto.getSoldBy());
        ad.setCondition(dto.getCondition());
        return ad;
    }

    public EventAd toEventAd(AdRequestDto dto, Subcategory subcategory) {
        EventAd ad = new EventAd();
        setCommonFields(ad, dto, subcategory);
        ad.setEventTypes(dto.getEventTypes());
        return ad;
    }

    public GigsAd toGigsAd(AdRequestDto dto, Subcategory subcategory) {
        GigsAd ad = new GigsAd();
        setCommonFields(ad, dto, subcategory);
        ad.setGigs(dto.getGigs());
        ad.setStatus(dto.getStatus());
        return ad;
    }

    public JobAd toJobAd(AdRequestDto dto, Subcategory subcategory) {
        JobAd ad = new JobAd();
        setCommonFields(ad, dto, subcategory);
        ad.setNonProfitOrganization(dto.getNonProfitOrganization());
        ad.setInternship(dto.getInternship());
        ad.setTelecommutingOk(dto.getTelecommutingOk());
        ad.setEmploymentType(dto.getEmploymentType());
        ad.setJobTypes(dto.getJobTypes());
        return ad;
    }

    public ServiceAd toServiceAd(AdRequestDto dto, Subcategory subcategory) {
        ServiceAd ad = new ServiceAd();
        setCommonFields(ad, dto, subcategory);
        ad.setServiceType(dto.getServiceType());
        return ad;
    }

    public void setCommonFields(Ad ad, AdRequestDto dto, Subcategory subcategory) {
        ad.setTitle(dto.getTitle());
        ad.setDescription(dto.getDescription());
        ad.setPrice(dto.getPrice());
        ad.setLocation(dto.getLocation());
        ad.setHasImage(dto.getHasImage());
        ad.setPostedToday(dto.getPostedToday());
        ad.setIsFree(dto.getIsFree());
        ad.setDeliveryAvailable(dto.getDeliveryAvailable());
        ad.setSubcategory(subcategory);
        ad.setCreatedAt(dto.getCreatedAt());
    }
}
