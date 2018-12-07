package com.yucn.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yucn.entity.PurchaseCommodity;
import com.yucn.entity.PurchaseOrder;
import com.yucn.entity.StockCommodity;
import com.yucn.service.PurchaseCommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 库存接口
 * Created by Administrator on 2018/11/28.
 */
@RestController
@RequestMapping("/stock")
@Slf4j
public class StockCommodityController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PurchaseCommodityService purchaseCommodityService;

    /**
     * 入库
     * @return 提交结果
     * @throws Exception
     */
    @PostMapping("/putIn")
    public ResultVO putIn(@RequestBody Map map) throws IOException{
        log.info("商品入库");
        PurchaseOrder purchaseOrder=objectMapper.readValue(objectMapper.writeValueAsString(map.get("purchaseOrder")), PurchaseOrder.class);
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(Set.class, PurchaseCommodity.class);
        Set<PurchaseCommodity> purchaseCommodities = objectMapper.readValue(objectMapper.writeValueAsString(map.get("purchaseCommodities")),javaType);
        Set<StockCommodity> stockCommodities=purchaseCommodityService.putInStorage(purchaseOrder, purchaseCommodities);

        return ResultVOUtil.success("入库成功", stockCommodities);
    }
}
