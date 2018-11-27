package com.yucn.repository;

import com.yucn.entity.Commodity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityRepositoryTest {

    @Autowired
    private CommodityRepository categoryRepository;

    @Test
    public void testSave() {
        Commodity commodity = new Commodity();
        commodity.setCode("123");
        commodity.setName("可口可乐");
        commodity.setSpecification("250ml");
        commodity.setUnit("瓶");
        commodity.setPrice(new BigDecimal(3.0));
        categoryRepository.save(commodity);
        Assert.assertNotNull(commodity.getId());
    }

}