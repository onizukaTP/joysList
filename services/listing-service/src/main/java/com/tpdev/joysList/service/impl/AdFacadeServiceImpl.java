package com.tpdev.joysList.service.impl;

import com.tpdev.events.AdCreatedEvent;
import com.tpdev.events.AdDeletedEvent;
import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.dto.AdResponse;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.CustomUserDetails;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.entity.enums.AdType;
import com.tpdev.joysList.exception.ResourceNotFound;
import com.tpdev.joysList.kafka.producer.AdEventProducer;
import com.tpdev.joysList.mapper.AdMapper;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.service.AdFacadeService;
import com.tpdev.joysList.service.category.*;
import com.tpdev.joysList.specification.AdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdFacadeServiceImpl implements AdFacadeService {

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
    private final AdEventProducer adEventProducer;

    @Override
    @CacheEvict(value = "ads", allEntries = true)
    public Ad createAd(AdRequestDto dto) {

        log.info("Incoming subcategoryId={}", dto.getSubcategoryId());
        log.info("Existing subcategories={}",
                subcategoryRepository.findAll()
                        .stream()
                        .map(Subcategory::getId)
                        .toList());

        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFound(
                        "Subcategory not found" + dto.getSubcategoryId()
                ));

        // validating subcategory
        if (!subcategory.getCategory().getName().equals(dto.getAdType())) {
            throw new ResourceNotFound(
                    "Subcategory does not belong to selected category");
        }

        Long postedById = getCurrentUserId();

        Ad savedAd = switch (dto.getAdType()) {
            case HOUSING  -> housingAdService.createAd(
                    mapper.toHousingAd(dto, subcategory, postedById));

            case FOR_SALE -> forSaleAdService.createAd(
                    mapper.toForSaleAd(dto, subcategory, postedById));

            case EVENTS   -> eventAdService.createAd(
                    mapper.toEventAd(dto, subcategory, postedById));

            case GIGS     -> gigsAdService.createAd(
                    mapper.toGigsAd(dto, subcategory, postedById));

            case JOBS     -> jobAdService.createAd(
                    mapper.toJobAd(dto, subcategory, postedById));

            case RESUMES  -> resumeAdService.createAd(
                    mapper.toResumeAd(dto, subcategory, postedById));

            case SERVICES -> serviceAdService.createAd(
                    mapper.toServiceAd(dto, subcategory, postedById));

            case COMMUNITY -> communityAdService.createAd(
                    mapper.toCommunityAd(dto, subcategory, postedById));
        };

        log.info("Ad created by userId {} under category {}", postedById, savedAd.getSubcategory().getName());

        adEventProducer.publishAdCreated(
                new AdCreatedEvent(
                        savedAd.getId(),
                        postedById,
                        savedAd.getTitle(),
                        savedAd.getSubcategory().getName()
                )
        );
        log.info("Ad Event published...");

        return savedAd;
    }

    @Override
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

    @Override
    public Page<AdResponse> search(
            String keyword,
            String location,
            Double minPrice,
            Double maxPrice,
            Boolean isFree,
            Boolean deliveryAvailable,
            Boolean postedToday,
            AdType category,
            Pageable pageable) {

        Specification<Ad> spec = Specification.allOf(
                AdSpecification.keywordMatches(keyword),
                AdSpecification.locationMatches(location),
                AdSpecification.minPrice(minPrice),
                AdSpecification.maxPrice(maxPrice),
                AdSpecification.isFree(isFree),
                AdSpecification.hasDelivery(deliveryAvailable),
                AdSpecification.postedToday(postedToday),
                AdSpecification.inCategory(category)
        );

        return repository.findAll(spec, pageable)
                .map(this::toResponse);
    }

    @Override
    public List<AdResponse> getAll() {
        log.info("Fetching ads from DB...");
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @CacheEvict(value = "ads", allEntries = true)
    public Ad updateAd(Long id, AdRequestDto dto) {
        Ad existingAd = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Ad not found with id: " + id));

        existingAd.setTitle(dto.getTitle());
        existingAd.setDescription(dto.getDescription());
        existingAd.setPrice(dto.getPrice());
        existingAd.setLocation(dto.getLocation());

        log.info("Ad {} updated by userId {}", id, getCurrentUserId());
        return repository.save(existingAd);
    }

    @Override
    @CacheEvict(value = "ads", allEntries = true)
    public void deleteAd(Long id) {
        Ad ad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Ad not found with id: " + id));

        repository.deleteById(id);
        log.info("Ad {} deleted by userId {}", id, getCurrentUserId());

        adEventProducer.publishAdDeleted(
                new AdDeletedEvent(
                        ad.getId(),
                        getCurrentUserId(),
                        ad.getTitle()
                )
        );
        log.info("Ad Deleted Event published for adId={}", id);
    }

    // Extract current authenticated user's ID from principal
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUser().getId();
        }
        throw new IllegalStateException("User not authenticated or invalid user details type");
    }

    // helper function to map Ad to AdResponse
    private AdResponse toResponse(Ad ad) {
        return new AdResponse(
                ad.getId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getPrice(),
                ad.getLocation(),
                ad.getSubcategory().getName(),
                ad.getPostedById()
        );
    }
}
