package com.yucn.repository;

import com.yucn.entity.Commodity;
import com.yucn.entity.PurchaseCommodity;
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
public class PurchaseCommodityRepositoryTest {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private PurchaseCommodityRepository purchaseCommodityRepository;

    @Test
    public void testSave(){
        PurchaseCommodity purchaseCommodity =new PurchaseCommodity();
        Commodity commodity = commodityRepository.getOne(1L);
        purchaseCommodity.setCommodity(commodity);
        purchaseCommodity.setPaidPrice(new BigDecimal(5));
        purchaseCommodity.setQuantity(10);
        purchaseCommodity.setTotalPrice(new BigDecimal(500));
        purchaseCommodityRepository.save(purchaseCommodity);

        Assert.assertNotNull(purchaseCommodity.getId());
    }

    @Test
    @Transactional
    public void testGet(){
        PurchaseCommodity purchaseCommodity = purchaseCommodityRepository.getOne(1L);
        Commodity commodity = purchaseCommodity.getCommodity();
        System.out.println(commodity.getName());
        Assert.assertNotNull(commodity);
    }

}