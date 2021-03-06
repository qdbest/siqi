package com.yucn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 库存类
 * Created by Administrator on 2018/11/22.
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class StockCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //商品
    @ManyToOne
    private Commodity commodity;
    //进价，默认0
    private BigDecimal paidPrice=new BigDecimal(0);
    //剩余数量，默认0
    private int surplusQuantity = 0;
    //删除标记，默认否
    @JsonIgnore
    private boolean deleted = false;
}
