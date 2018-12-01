package com.yucn.service.impl;

import com.yucn.entity.Commodity;
import com.yucn.entity.StockCommodity;
import com.yucn.exception.YucnException;
import com.yucn.repository.CommodityRepository;
import com.yucn.repository.StockCommodityRepository;
import com.yucn.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.yucn.enums.ResultEnum.CODE_EXIST;

/**
 * Created by Administrator on 2018/11/22.
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;

    @Override
    public Commodity findByCode(String code) {
        return commodityRepository.findByCodeAndDeletedFalse(code);
    }

    @Override
    public void add(Commodity commodity) {
        try {
            commodityRepository.save(commodity);
            //同步添加相应库存
            StockCommodity stockCommodity = new StockCommodity();
            stockCommodity.setCommodity(commodity);
            stockCommodityRepository.save(stockCommodity);
        } catch (Exception e) {
            throw new YucnException(CODE_EXIST);
        }
    }

    @Override
    public Page<Commodity> list(PageRequest request) {
        Page<Commodity> commodityPage = commodityRepository.findAllByDeletedFalse(request);
        return commodityPage;
    }
}
