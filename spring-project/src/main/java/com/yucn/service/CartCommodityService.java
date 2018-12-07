package com.yucn.service;

import com.yucn.entity.CartCommodity;
import com.yucn.entity.SaleOrder;

import java.util.Set;

/**
 * Created by Administrator on 2018/11/23.
 */
public interface CartCommodityService {
    CartCommodity delete(CartCommodity cartCommodity);

    SaleOrder receive(SaleOrder saleOrder, Set<CartCommodity> cartCommodities);
}
