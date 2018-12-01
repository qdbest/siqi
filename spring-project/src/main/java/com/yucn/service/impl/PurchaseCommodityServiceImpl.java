package com.yucn.service.impl;

import com.yucn.entity.Commodity;
import com.yucn.entity.PurchaseCommodity;
import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;
import com.yucn.exception.YucnException;
import com.yucn.repository.CommodityRepository;
import com.yucn.repository.PurchaseCommodityRepository;
import com.yucn.repository.StockCommodityRepository;
import com.yucn.service.PurchaseCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static com.yucn.enums.ResultEnum.DATABASE_ERROR;

/**
 * Created by Administrator on 2018/11/23.
 */
@Service
public class PurchaseCommodityServiceImpl implements PurchaseCommodityService {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private PurchaseCommodityRepository purchaseCommodityRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;

    @Override
    @Transactional
    public Set<StockCommodity> putInStorage(PurchaseOrder purchaseOrder, Set<PurchaseCommodity> purchaseCommodities) {
        Set<StockCommodity> stockCommodities = new HashSet<>();
        purchaseCommodities.forEach(purchaseCommodity -> {
            purchaseCommodity.setPurchaseOrder(purchaseOrder);
            Commodity commodity = commodityRepository.getOne(purchaseCommodity.getCommodity().getId());
            purchaseCommodity.setCommodity(commodity);

            //同步更新库存
            StockCommodity stockCommodity = stockCommodityRepository.findByCommodityAndDeletedFalse(commodity);
            stockCommodity.setPaidPrice(purchaseCommodity.getPaidPrice());
            stockCommodity.setSurplusQuantity(stockCommodity.getSurplusQuantity() + purchaseCommodity.getQuantity());
            stockCommodities.add(stockCommodity);
        });
        try {
            purchaseCommodityRepository.saveAll(purchaseCommodities);
            stockCommodityRepository.saveAll(stockCommodities);
        } catch (Exception e) {
            throw new YucnException(DATABASE_ERROR);
        }

        return stockCommodities;
    }
}
