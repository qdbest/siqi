package com.yucn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**购物车商品类
 * Created by Administrator on 2018/11/30.
 */
@Entity
@Getter
@Setter
//jpa懒加载会产生代理类，转换json的时候无法发射以下属性，系统会报错，因此忽略掉以下属性的json转换
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class CartCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //库存
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StockCommodity stockCommodity;
    //数量，默认为1
    private int quantity = 1;
    //删除标记，默认否
    @JsonIgnore
    private boolean deleted = false;
}
