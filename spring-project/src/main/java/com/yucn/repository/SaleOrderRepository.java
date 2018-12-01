package com.yucn.repository;

import com.yucn.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/11/28.
 */
public interface SaleOrderRepository extends JpaRepository<SaleOrder,Long> {
    SaleOrder findByCodeAndDeletedFalse(String code);
}
