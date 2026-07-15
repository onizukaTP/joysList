package com.tpdev.joysList.service.category;

import com.tpdev.joysList.entity.category.ForSaleAd;
import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.enums.SoldBy;
import com.tpdev.joysList.service.IBaseAdService;

import java.util.List;

public interface ForSaleAdService extends IBaseAdService<ForSaleAd> {

    List<ForSaleAd> filter(SoldBy soldBy, Condition condition);
}
