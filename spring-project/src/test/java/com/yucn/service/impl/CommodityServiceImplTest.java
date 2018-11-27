package com.yucn.service.impl;

import com.yucn.entity.Commodity;
import com.yucn.service.CommodityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2018/11/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityServiceImplTest {
    @Autowired
    private CommodityService commodityService;
    @Test
    public void testFindByCode() {
        Commodity commodity = commodityService.findByCode("123");
        Assert.assertNotNull(commodity);
    }

}