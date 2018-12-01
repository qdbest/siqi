package com.yucn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SaleCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private SaleOrder saleOrder;
    @OneToOne
    private CartCommodity cartCommodity;
    //删除标记，默认否
    @JsonIgnore
    private boolean deleted = false;
}
