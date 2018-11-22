package com.yucn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/22.
 */
@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //二维码
    private String code;
    //名称
    private String name;
    //规格
    private String specification;
    //单位
    private String unit;
    //单价
    private BigDecimal price;
}
