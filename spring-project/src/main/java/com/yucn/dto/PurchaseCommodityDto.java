package com.yucn.dto;

import com.yucn.entity.PurchaseCommodity;
import com.yucn.entity.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Administrator on 2018/11/27.
 */
@Getter
@Setter
public class PurchaseCommodityDto {
    private PurchaseOrder purchaseOrder;
    private Set<PurchaseCommodity> purchaseCommodities;
}
