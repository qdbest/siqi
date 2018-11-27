package com.yucn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 进货类
 * Created by Administrator on 2018/11/22.
 */
@Entity
@Getter
@Setter
public class StockCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //商品
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Commodity commodity;
    //进价
    private BigDecimal paidPrice;
    //进货数量
    private int quantity;
    //进货总价
    private BigDecimal totalPrice;
    //剩余数量
    private int surplusQuantity;
    //进货单
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PurchaseOrder purchaseOrder;
    //删除标记
    private boolean deleted = false;
}
