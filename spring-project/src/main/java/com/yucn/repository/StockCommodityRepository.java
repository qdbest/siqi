package com.yucn.repository;

import com.yucn.entity.Commodity;
import com.yucn.entity.StockCommodity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/11/28.
 */
public interface StockCommodityRepository extends JpaRepository<StockCommodity,Long> {

    StockCommodity findByCommodityAndDeletedFalse(Commodity commodity);
}
