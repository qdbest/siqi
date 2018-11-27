package com.yucn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 销售商品类
 * Created by Administrator on 2018/11/22.
 */
@Entity
@Getter
@Setter
public class SaleCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //商品
    @ManyToOne
    private Commodity commodity;
    //进货商品，实为进货批次
    @ManyToOne
    private StockCommodity stockCommodity;
    //数量，默认为1
    private int quantity = 1;
    //删除标记，默认否
    private boolean deleted = false;
}
