package com.yucn.service.impl;

import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;
import com.yucn.repository.CommodityRepository;
import com.yucn.repository.StockCommodityRepository;
import com.yucn.service.StockCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Created by Administrator on 2018/11/23.
 */
@Service
public class StockCommodityServiceImpl implements StockCommodityService {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;

    @Override
    @Transactional
    public void addAll(PurchaseOrder purchaseOrder, Set<StockCommodity> stockCommodities) throws Exception {
        stockCommodities.forEach(stockCommodity -> {
            stockCommodity.setPurchaseOrder(purchaseOrder);
            stockCommodity.setCommodity(commodityRepository.getOne(stockCommodity.getCommodity().getId()));
        });
        try {
            stockCommodityRepository.saveAll(stockCommodities);
        } catch (Exception e) {
            throw new Exception("插入数据库错误，请检查是否填写完整！");
        }
    }
}
