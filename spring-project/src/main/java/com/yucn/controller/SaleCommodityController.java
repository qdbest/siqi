package com.yucn.controller;

import com.yucn.dto.BillDto;
import com.yucn.entity.CartCommodity;
import com.yucn.entity.SaleOrder;
import com.yucn.service.SaleCommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/11/28.
 */
@RestController
@RequestMapping("/saleCommodity")
public class SaleCommodityController {
    @Autowired
    SaleCommodityService saleCommodityService;

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody CartCommodity cartCommodity) {
        saleCommodityService.delete(cartCommodity);
        return ResultVOUtil.success("删除成功", null);
    }

    @PostMapping("/receive")
    public ResultVO receive(@RequestBody BillDto billDto) {
        SaleOrder savedSaleOrder = saleCommodityService.receive(billDto.getSaleOrder(),billDto.getCartCommodities());
        return ResultVOUtil.success("结账成功", null);
    }

}
