package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.CommunityAd;
import com.tpdev.joysList.entity.enums.CommunityType;
import com.tpdev.joysList.entity.enums.LostAndFound;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface CommunityAdService extends IBaseAdService<CommunityAd> {

    List<CommunityAd> filter(CommunityType communityType, LostAndFound lostOrFound);
}
