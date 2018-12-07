package com.yucn.init;

import com.yucn.entity.Commodity;
import com.yucn.entity.PurchaseCommodity;
import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;
import com.yucn.repository.CommodityRepository;
import com.yucn.repository.PurchaseCommodityRepository;
import com.yucn.repository.StockCommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/22.
 */
@Service
public class InitData {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private PurchaseCommodityRepository purchaseCommodityRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;

    @Transactional
    @PostConstruct
    public void InitData(){
        Commodity commodity = new Commodity();
        commodity.setCode("123");
        commodity.setName("可口可乐");
        commodity.setSpecification("250ml");
        commodity.setUnit("瓶");
        commodity.setPrice(new BigDecimal(5));
        commodityRepository.save(commodity);

        StockCommodity stockCommodity = new StockCommodity();
        stockCommodity.setCommodity(commodity);
        stockCommodityRepository.save(stockCommodity);

        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setCode("p123");
        purchaseOrder.setSeller("1号店");
        purchaseOrder.setPay(new BigDecimal(30));
        purchaseOrder.setRealPay(new BigDecimal(30));

        PurchaseCommodity purchaseCommodity =new PurchaseCommodity();
        purchaseCommodity.setCommodity(commodity);
        purchaseCommodity.setPaidPrice(new BigDecimal(3));
        purchaseCommodity.setQuantity(10);
        purchaseCommodity.setTotalPrice(new BigDecimal(50));
        purchaseCommodity.setPurchaseOrder(purchaseOrder);

        purchaseCommodityRepository.save(purchaseCommodity);

        stockCommodity.setPaidPrice(purchaseCommodity.getPaidPrice());
        stockCommodity.setSurplusQuantity(stockCommodity.getSurplusQuantity()+purchaseCommodity.getQuantity());

        stockCommodityRepository.save(stockCommodity);

    }
}
