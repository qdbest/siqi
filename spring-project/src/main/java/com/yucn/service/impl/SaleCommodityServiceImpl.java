package com.yucn.service.impl;

import com.yucn.entity.CartCommodity;
import com.yucn.entity.SaleCommodity;
import com.yucn.entity.SaleOrder;
import com.yucn.entity.StockCommodity;
import com.yucn.exception.YucnException;
import com.yucn.repository.CartCommodityRepository;
import com.yucn.repository.SaleCommodityRepository;
import com.yucn.repository.SaleOrderRepository;
import com.yucn.repository.StockCommodityRepository;
import com.yucn.service.SaleCommodityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static com.yucn.enums.ResultEnum.DATABASE_ERROR;

/**
 * Created by Administrator on 2018/11/23.
 */
@Service
public class SaleCommodityServiceImpl implements SaleCommodityService {
    @Autowired
    private StockCommodityRepository stockCommodityRepository;
    @Autowired
    private SaleCommodityRepository saleCommodityRepository;
    @Autowired
    private CartCommodityRepository cartCommodityRepository;
    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Override
    public CartCommodity delete(CartCommodity cartCommodity) {
        //加库存
        StockCommodity stockCommodity = stockCommodityRepository.getOne(cartCommodity.getStockCommodity().getId());
        stockCommodity.setSurplusQuantity(stockCommodity.getSurplusQuantity() + cartCommodity.getQuantity());
        try {
            //删除
            cartCommodityRepository.delete(cartCommodity);
            stockCommodityRepository.save(stockCommodity);
        } catch (Exception e) {
            throw new YucnException(DATABASE_ERROR);
        }
        return cartCommodity;
    }

    @Transactional
    @Override
    public SaleOrder receive(SaleOrder saleOrder, Set<CartCommodity> cartCommodities) {
        try {
            saleOrderRepository.save(saleOrder);
        } catch (Exception e) {
            throw new YucnException(DATABASE_ERROR);
        }

        Set<SaleCommodity> saleCommodities = new HashSet<>();
        cartCommodities.forEach(cartCommodity -> {
            SaleCommodity saleCommodity = new SaleCommodity();
            saleCommodity.setSaleOrder(saleOrder);
            saleCommodity.setCartCommodity(cartCommodity);
            saleCommodities.add(saleCommodity);
        });

        try {
            saleCommodityRepository.saveAll(saleCommodities);
        } catch (Exception e) {
            throw new YucnException(DATABASE_ERROR);
        }
        return saleOrder;
    }
}
