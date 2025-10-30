package com.tpdev.joysList.specification;

import com.tpdev.joysList.entity.enums.Condition;
import com.tpdev.joysList.entity.category.ForSaleAd;
import com.tpdev.joysList.entity.enums.SoldBy;
import org.springframework.data.jpa.domain.Specification;

public class ForSaleAdSpecifications {
    public static Specification<ForSaleAd> soldBy(SoldBy soldBy) {
        return (root, query, cb) ->
                soldBy == null ? cb.conjunction() :
                        cb.equal(root.get("soldBy"), soldBy);
    }

    public static Specification<ForSaleAd> condition(Condition condition) {
        return (root, query, cb) ->
                condition == null ? cb.conjunction() :
                        cb.equal(root.get("condition"), condition);
    }
}
