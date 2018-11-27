package com.yucn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品类，商品如果要修改售价等信息，将记录做删除标记，重新复制一条，避免售价等信息丢失
 * Created by Administrator on 2018/11/22.
 */
@Entity
@Getter
@Setter
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //条码
    @Column(unique = true,nullable = false)
    private String code;
    //名称
    @Column(nullable = false)
    private String name;
    //规格
    private String specification;
    //单位
    private String unit;
    //零售价
    private BigDecimal price;
    //删除标记
    private boolean deleted = false;
}
