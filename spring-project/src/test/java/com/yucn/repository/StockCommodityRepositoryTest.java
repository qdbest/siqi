package com.yucn.repository;

import com.yucn.entity.Commodity;
import com.yucn.entity.StockCommodity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockCommodityRepositoryTest {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;

    @Test
    public void testSave(){
        StockCommodity stockCommodity =new StockCommodity();
        Commodity commodity = commodityRepository.getOne(1L);
        stockCommodity.setCommodity(commodity);
        stockCommodity.setPaidPrice(new BigDecimal(5));
        stockCommodity.setQuantity(10);
        stockCommodity.setTotalPrice(new BigDecimal(500));
        stockCommodityRepository.save(stockCommodity);

        Assert.assertNotNull(stockCommodity.getId());
    }

    @Test
    @Transactional
    public void testGet(){
        StockCommodity stockCommodity = stockCommodityRepository.getOne(1L);
        Commodity commodity = stockCommodity.getCommodity();
        System.out.println(commodity.getName());
        Assert.assertNotNull(commodity);
    }

}