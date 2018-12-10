package com.yucn.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yucn.entity.CartCommodity;
import com.yucn.entity.SaleOrder;
import com.yucn.service.CartCommodityService;
import com.yucn.service.StockCommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/12/1.
 */
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
    @Autowired
    private StockCommodityService stockCommodityService;
    @Autowired
    private CartCommodityService cartCommodityService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 购物车增加一个商品
     *
     * @param commodityCode 商品条码
     * @return 增加的销售商品
     */
    @GetMapping("/{commodityCode}")
    public ResultVO add(@PathVariable String commodityCode) {
        log.info("购物车增加一个商品");
        //减少库存
        CartCommodity cartCommodity = stockCommodityService.reduceOne(commodityCode);
        return ResultVOUtil.success(null, cartCommodity);
    }

    @PutMapping
    public ResultVO change(@RequestBody CartCommodity cartCommodity) {
        log.info("购物车商品修改数量");
        cartCommodity = stockCommodityService.reduce(cartCommodity);
        return ResultVOUtil.success(null, cartCommodity);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Long id) {
        log.info("从购物车删除一个商品");
        cartCommodityService.delete(id);
        return ResultVOUtil.success("删除成功", null);
    }

    @PostMapping("/receive")
    public ResultVO receive(@RequestBody Map map) throws IOException {
        log.info("购物车结账");
        SaleOrder saleOrder = objectMapper.readValue(objectMapper.writeValueAsString(map.get("saleOrder")), SaleOrder.class);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Set.class, CartCommodity.class);
        Set<CartCommodity> cartCommodities = objectMapper.readValue(objectMapper.writeValueAsString(map.get("cartCommodities")), javaType);

        SaleOrder savedSaleOrder = cartCommodityService.receive(saleOrder, cartCommodities);
        return ResultVOUtil.success("结账成功", savedSaleOrder);
    }
}
