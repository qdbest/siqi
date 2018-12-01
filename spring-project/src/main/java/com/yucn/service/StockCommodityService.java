package com.yucn.service;

import com.yucn.entity.CartCommodity;

/**
 * Created by Administrator on 2018/11/28.
 */
public interface StockCommodityService {
    CartCommodity reduceOne(String commodityCode);

    CartCommodity reduce(CartCommodity cartCommodity);
}
