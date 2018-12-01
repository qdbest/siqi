package com.yucn.service;

import com.yucn.entity.PurchaseCommodity;
import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;

import java.util.Set;

/**
 * Created by Administrator on 2018/11/23.
 */
public interface PurchaseCommodityService {
    Set<StockCommodity> putInStorage(PurchaseOrder purchaseOrder, Set<PurchaseCommodity> purchaseCommodities);
}
