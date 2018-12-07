package com.yucn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进货单
 * Created by Administrator on 2018/11/26.
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //进货单号
    private String code;
    //供货商
    private String seller;
    //应付
    private BigDecimal pay;
    //实付
    private BigDecimal realPay;
    //生成时间
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;
    //删除标记，默认否
    @JsonIgnore
    private boolean deleted = false;
}
