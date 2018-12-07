package com.yucn.service.impl;

import com.yucn.entity.Commodity;
import com.yucn.entity.StockCommodity;
import com.yucn.exception.YucnException;
import com.yucn.repository.CommodityRepository;
import com.yucn.repository.StockCommodityRepository;
import com.yucn.service.CommodityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.yucn.enums.ResultEnum.CODE_EXIST;
import static com.yucn.enums.ResultEnum.COMMODITY_NOT_EXIST;
import static com.yucn.enums.ResultEnum.DATABASE_ERROR;

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

    @Override
    public Commodity update(Commodity commodity) {
        Commodity originalCommodity = commodityRepository.getOne(commodity.getId());
        if (originalCommodity == null) {
            throw new YucnException(COMMODITY_NOT_EXIST);
        }
        BeanUtils.copyProperties(commodity, originalCommodity);

        try {
            commodityRepository.save(originalCommodity);
        } catch (Exception e){
            throw new YucnException(DATABASE_ERROR);
        }

        return originalCommodity;
    }

    @Override
    public Commodity delete(Long id) {
        Commodity commodity = commodityRepository.getOne(id);
        if (commodity == null) {
            throw new YucnException(COMMODITY_NOT_EXIST);
        }

        commodity.setDeleted(true);
        try {
            commodityRepository.save(commodity);
        } catch (Exception e){
            throw new YucnException(DATABASE_ERROR);
        }

        return commodity;
    }
}
