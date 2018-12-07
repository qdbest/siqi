package com.yucn.service;

import com.yucn.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by Administrator on 2018/11/22.
 */
public interface CommodityService {
    Commodity findByCode(String code);

    void add(Commodity commodity);

    Page<Commodity> list(PageRequest request);

    Commodity update(Commodity commodity);

    Commodity delete(Long commodity);
}
