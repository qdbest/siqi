package com.yucn.init;

import com.yucn.entity.Commodity;
import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;
import com.yucn.repository.CommodityRepository;
import com.yucn.repository.PurchaseOrderRepository;
import com.yucn.repository.StockCommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/22.
 */
@Service
public class InitData {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;
    @PostConstruct
    public void InitData(){
        Commodity commodity = new Commodity();
        commodity.setCode("123");
        commodity.setName("可口可乐");
        commodity.setSpecification("250ml");
        commodity.setUnit("瓶");
        commodity.setPrice(new BigDecimal(3.0));

        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setCode("p123");
        purchaseOrder.setSeller("1号店");

        StockCommodity stockCommodity =new StockCommodity();
        stockCommodity.setCommodity(commodity);
        stockCommodity.setPaidPrice(new BigDecimal(5));
        stockCommodity.setQuantity(10);
        stockCommodity.setTotalPrice(new BigDecimal(50));
        stockCommodity.setSurplusQuantity(10);

        purchaseOrder.getStockCommodities().add(stockCommodity);
        stockCommodity.setPurchaseOrder(purchaseOrder);
        stockCommodityRepository.save(stockCommodity);
    }
}
