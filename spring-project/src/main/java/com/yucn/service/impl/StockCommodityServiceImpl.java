package com.yucn.service.impl;

import com.yucn.entity.CartCommodity;
import com.yucn.entity.Commodity;
import com.yucn.entity.StockCommodity;
import com.yucn.exception.YucnException;
import com.yucn.repository.*;
import com.yucn.service.StockCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.yucn.enums.ResultEnum.*;

/**
 * Created by Administrator on 2018/11/28.
 */
@Service
public class StockCommodityServiceImpl implements StockCommodityService {
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private StockCommodityRepository stockCommodityRepository;
    @Autowired
    private CartCommodityRepository cartCommodityRepository;
    @Autowired
    private SaleOrderRepository saleOrderRepository;
    @Autowired
    private SaleCommodityRepository saleCommodityRepository;

    @Override
    public CartCommodity reduceOne(String commodityCode) {
        Commodity commodity = commodityRepository.findByCodeAndDeletedFalse(commodityCode);
        //商品是否存在
        if (commodity == null) {
            throw new YucnException(COMMODITY_NOT_EXIST);
        }
        //库存是否不足
        StockCommodity stockCommodity = stockCommodityRepository.findByCommodityAndDeletedFalse(commodity);
        if (stockCommodity.getSurplusQuantity() < 1) {
            throw new YucnException(STOCK_IS_LOW);
        }

        CartCommodity cartCommodity = new CartCommodity();
        cartCommodity.setStockCommodity(stockCommodity);
        stockCommodity.setSurplusQuantity(stockCommodity.getSurplusQuantity() - 1);
        try {
            cartCommodityRepository.save(cartCommodity);
        } catch (Exception e) {
            throw new YucnException(DATABASE_ERROR);
        }
        return cartCommodity;
    }

    @Transactional
    @Override
    public CartCommodity reduce(CartCommodity cartCommodity) {
        CartCommodity originalCartCommodity = cartCommodityRepository.getOne(cartCommodity.getId());

        //库存是否不足
        StockCommodity stockCommodity = stockCommodityRepository.findByCommodityAndDeletedFalse(cartCommodity.getStockCommodity().getCommodity());
        if (stockCommodity.getSurplusQuantity() < cartCommodity.getQuantity()-originalCartCommodity.getQuantity()) {
            throw new YucnException(STOCK_IS_LOW);
        }

        stockCommodity.setSurplusQuantity(stockCommodity.getSurplusQuantity() - (cartCommodity.getQuantity()-originalCartCommodity.getQuantity()));
        originalCartCommodity.setQuantity(cartCommodity.getQuantity());

        //此处可以不显式保存，脏数据写即可
        try {
            cartCommodityRepository.save(originalCartCommodity);
            stockCommodityRepository.save(stockCommodity);
        } catch (Exception e) {
            throw new YucnException(DATABASE_ERROR);
        }

        return originalCartCommodity;
    }
}
