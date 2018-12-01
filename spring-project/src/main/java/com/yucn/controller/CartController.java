package com.yucn.controller;

import com.yucn.entity.CartCommodity;
import com.yucn.service.StockCommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/12/1.
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private StockCommodityService stockCommodityService;

    /**
     * 购物车增加一个商品
     * @param commodityCode 商品条码
     * @return 增加的销售商品
     */
    @GetMapping("/add")
    public ResultVO reduceOne(@RequestParam String commodityCode) {
        CartCommodity cartCommodity = stockCommodityService.reduceOne(commodityCode);
        return ResultVOUtil.success(null, cartCommodity);
    }

    @PostMapping("/change")
    public ResultVO reduce(@RequestBody CartCommodity cartCommodity) {
        cartCommodity = stockCommodityService.reduce(cartCommodity);
        return ResultVOUtil.success(null, cartCommodity);
    }
}
