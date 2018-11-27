package com.yucn.dto;

import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Administrator on 2018/11/27.
 */
@Getter
@Setter
public class StockCommodityDto {
    private PurchaseOrder purchaseOrder;
    private Set<StockCommodity> stockCommodities;
}
