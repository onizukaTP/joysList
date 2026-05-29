package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.CommunityAd;
import com.tpdev.joysList.entity.enums.CommunityType;
import com.tpdev.joysList.entity.enums.LostAndFound;
import com.tpdev.joysList.repo.category.CommunityAdRepository;
import com.tpdev.joysList.service.BaseAdService;
import com.tpdev.joysList.specification.CommunityAdSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityAdService extends BaseAdService<CommunityAd> {
    private final CommunityAdRepository repository;

    public CommunityAdService(CommunityAdRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public CommunityAd createAd(CommunityAd communityAd) {
        repository.save(communityAd);
        return communityAd;
    }

    public List<CommunityAd> filter(CommunityType communityType, LostAndFound lostOrFound) {
        List<Specification<CommunityAd>> specs = new ArrayList<>();

        if (communityType != null) specs.add(CommunityAdSpecifications.hasCommunityType(communityType));
        if (lostOrFound != null) specs.add(CommunityAdSpecifications.lostOrFound(lostOrFound));

        Specification<CommunityAd> finalSpec = specs.stream()
                .reduce(Specification::and).orElse(null);
        return repository.findAll(finalSpec);
    }
}
