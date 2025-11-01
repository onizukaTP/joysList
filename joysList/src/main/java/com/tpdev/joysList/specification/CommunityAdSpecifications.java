package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.category.CommunityAd;
import com.tpdev.joysList.entity.enums.CommunityType;
import com.tpdev.joysList.entity.enums.LostAndFound;
import org.springframework.data.jpa.domain.Specification;

public class CommunityAdSpecifications {
    public static Specification<CommunityAd> hasCommunityType(CommunityType communityType) {
        return (root, query, criteriaBuilder) ->
                communityType == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("communityType"), communityType);
    }

    public static Specification<CommunityAd> lostOrFound(LostAndFound lostOrFound) {
        return (root, query, criteriaBuilder) ->
                lostOrFound == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("lostOrFound"), lostOrFound);
    }
}
