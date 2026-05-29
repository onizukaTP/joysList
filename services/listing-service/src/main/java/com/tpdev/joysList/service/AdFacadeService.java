package com.tpdev.joysList.service;

import com.tpdev.joysList.dto.AdCreatedEvent;
import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.CustomUserDetails;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.kafka.producer.AdEventProducer;
import com.tpdev.joysList.mapper.AdMapper;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.service.category.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdFacadeService {

    private final HousingAdService housingAdService;
    private final ForSaleAdService forSaleAdService;
    private final EventAdService eventAdService;
    private final GigsAdService gigsAdService;
    private final JobAdService jobAdService;
    private final ResumeAdService resumeAdService;
    private final ServiceAdService serviceAdService;
    private final CommunityAdService communityAdService;
    private final AdMapper mapper;
    private final SubcategoryRepository subcategoryRepository;
    private final AdRepository repository;

    private AdEventProducer adEventProducer;

    @CacheEvict(value = "ads", allEntries = true)
    public Ad createAd(AdRequestDto dto) {
        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subcategory ID"));

        UserEntity currentUser = getCurrentUser();

        Ad savedAd =  switch (dto.getAdType().toUpperCase()) {
            case "HOUSING"   -> housingAdService.createAd(mapper.toHousingAd(dto, subcategory, currentUser));
            case "FOR_SALE"  -> forSaleAdService.createAd(mapper.toForSaleAd(dto, subcategory, currentUser));
            case "EVENT"     -> eventAdService.createAd(mapper.toEventAd(dto, subcategory, currentUser));
            case "GIGS"      -> gigsAdService.createAd(mapper.toGigsAd(dto, subcategory, currentUser));
            case "JOBS"      -> jobAdService.createAd(mapper.toJobAd(dto, subcategory, currentUser));
            case "RESUME"    -> resumeAdService.createAd(mapper.toResumeAd(dto, subcategory, currentUser));
            case "SERVICE"   -> serviceAdService.createAd(mapper.toServiceAd(dto, subcategory, currentUser));
            case "COMMUNITY" -> communityAdService.createAd(mapper.toCommunityAd(dto, subcategory, currentUser));
            default -> throw new IllegalArgumentException("Unknown ad type: " + dto.getAdType());
        };

        log.info("Ad created by user {} under category {}", currentUser.getUsername(), savedAd.getSubcategory().getName());

        adEventProducer.publishAdCreated(
                new AdCreatedEvent(
                        savedAd.getId(),
                        currentUser.getId(),
                        savedAd.getTitle(),
                        savedAd.getSubcategory().getName()
                )
        );

        return savedAd;
    }

    @CacheEvict(value = "ads", allEntries = true)
    public List<Ad> createMultipleAds(List<AdRequestDto> dto) {
        int adCount = 1;
        List<Ad> adList = new ArrayList<>();
        for (AdRequestDto ad : dto) {
            adList.add(createAd(ad));
            adCount++;
        }
        log.info("{} number of Ads created", adCount);
        return adList;
    }

    public List<Ad> searchAdByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    @Cacheable(value = "ads")
    public List<Ad> getAll() {
        log.info("Fetching ads from DB...");
        return repository.findAll();
    }

    // Pulls the logged-in user out of the security context.
    // The JwtAuthenticationFilter already put a CustomUserDetails object here
    // when it validated the Bearer token, so this is always safe to call
    // on any protected endpoint.
    private UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }
}