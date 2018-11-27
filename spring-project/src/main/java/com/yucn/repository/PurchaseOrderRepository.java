package com.yucn.repository;

import com.yucn.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
