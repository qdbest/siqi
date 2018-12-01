package com.yucn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**进货类
 * Created by Administrator on 2018/11/28.
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class PurchaseCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //商品
    @ManyToOne
    private Commodity commodity;
    //进价
    private BigDecimal paidPrice;
    //进货数量
    private int quantity;
    //进货总价
    private BigDecimal totalPrice;
    //进货单
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PurchaseOrder purchaseOrder;
    //删除标记
    @JsonIgnore
    private boolean deleted = false;
}
