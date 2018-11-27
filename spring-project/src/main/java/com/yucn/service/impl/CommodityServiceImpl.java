package com.yucn.service.impl;

import com.yucn.entity.Commodity;
import com.yucn.repository.CommodityRepository;
import com.yucn.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/11/22.
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityRepository commodityRepository;
    @Override
    public Commodity findByCode(String code) {
        return commodityRepository.findByCodeAndDeletedFalse(code);
    }

    @Override
    public void add(Commodity commodity) throws Exception{
        try {
        commodityRepository.save(commodity);
        } catch (Exception e){
            throw new Exception("插入数据库错误，请检查条形码是否已存在！");
        }
    }

    @Override
    public Page<Commodity> list(PageRequest request) {
        Page<Commodity> commodityPage= commodityRepository.findAllByDeletedFalse(request);
        return commodityPage;
    }
}
