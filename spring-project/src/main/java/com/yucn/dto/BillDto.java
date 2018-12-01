package com.yucn.dto;

import com.yucn.entity.CartCommodity;
import com.yucn.entity.SaleOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Administrator on 2018/11/30.
 */
@Getter
@Setter
public class BillDto {
    private SaleOrder saleOrder;
    private Set<CartCommodity> cartCommodities;
}
