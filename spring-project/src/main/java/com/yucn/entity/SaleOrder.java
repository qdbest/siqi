package com.yucn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2018/11/28.
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //销售单号
    private String code;
    //商品列表
    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "saleOrder")
    private Set<SaleCommodity> saleCommodities;
    //应收
    private BigDecimal income;
    //实收
    private BigDecimal realIncome;
    //找零
    private BigDecimal giveChange;
    //创建时间
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;
    //已付款
    private boolean paid = false;
    //删除标记，默认否
    @JsonIgnore
    private boolean deleted = false;
}
