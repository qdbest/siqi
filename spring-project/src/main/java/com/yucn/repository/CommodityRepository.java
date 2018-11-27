package com.yucn.repository;

import com.yucn.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/11/22.
 */
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    Commodity findByCodeAndDeletedFalse(String code);

    Page<Commodity> findAllByDeletedFalse(Pageable pageable);
}
