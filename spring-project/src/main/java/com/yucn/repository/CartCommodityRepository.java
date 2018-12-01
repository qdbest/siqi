package com.yucn.repository;

import com.yucn.entity.CartCommodity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/11/30.
 */
public interface CartCommodityRepository extends JpaRepository<CartCommodity,Long> {
}
