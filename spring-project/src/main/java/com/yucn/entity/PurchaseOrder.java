package com.yucn.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 进货单
 * Created by Administrator on 2018/11/26.
 */
@Entity
@Getter
@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //进货单号
    private String code;
    //供货商
    private String seller;
    //进货明细
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "purchaseOrder")
    private Set<StockCommodity> stockCommodities=new HashSet<>();
}
