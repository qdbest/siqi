package com.yucn.controller;

import com.yucn.dto.PurchaseCommodityDto;
import com.yucn.entity.StockCommodity;
import com.yucn.service.PurchaseCommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 库存接口
 * Created by Administrator on 2018/11/28.
 */
@RestController
@RequestMapping("/stock")
public class StockCommodityController {

    @Autowired
    private PurchaseCommodityService purchaseCommodityService;

    /**
     * 入库
     * @param purchaseCommodityDto 进货传值类
     * @return 提交结果
     * @throws Exception
     */
    @PostMapping("/putIn")
    public ResultVO addAll(@RequestBody PurchaseCommodityDto purchaseCommodityDto) {
        Set<StockCommodity> stockCommodities = purchaseCommodityService.putInStorage(purchaseCommodityDto.getPurchaseOrder(), purchaseCommodityDto.getPurchaseCommodities());

        return ResultVOUtil.success("入库成功", stockCommodities);
    }
}
