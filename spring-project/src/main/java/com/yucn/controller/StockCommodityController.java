package com.yucn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yucn.dto.StockCommodityDto;
import com.yucn.service.StockCommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/11/26.
 */
@RestController
@RequestMapping("/stockCommodity")
public class StockCommodityController {
    @Autowired
    private StockCommodityService stockCommodityService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/addAll")
    public ResultVO<String> addAll(@RequestBody StockCommodityDto stockCommodityDto) throws Exception {
        stockCommodityService.addAll(stockCommodityDto.getPurchaseOrder(), stockCommodityDto.getStockCommodities());

        return ResultVOUtil.success("提交成功");
    }
}
