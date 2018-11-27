package com.yucn.controller;

import com.yucn.entity.Commodity;
import com.yucn.service.CommodityService;
import com.yucn.utils.ResultVOUtil;
import com.yucn.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/11/23.
 */
@RestController
@RequestMapping("/commodity")
@Slf4j
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @GetMapping
    public ResultVO<List<Commodity>> list(@RequestParam(value = "currentPage") Integer page,
                                          @RequestParam(value = "pageSize") Integer size) {
        log.info("查询商品列表");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest request = PageRequest.of(page - 1, size, sort);
        Page<Commodity> commodityPage = commodityService.list(request);
        System.out.println(commodityPage);
        return ResultVOUtil.success(commodityPage);
    }

    @GetMapping("/find")
    public ResultVO<Commodity> findByCode(@RequestParam(value = "code") String code){
        log.info("根据条码查询商品是否已经存在");
        Commodity commodity=commodityService.findByCode(code);
        return ResultVOUtil.success(commodity);
    }

    @PostMapping("/add")
    public ResultVO<Commodity> add(@RequestBody Commodity commodity) throws Exception {
        log.info("添加商品");
        if(commodity.equals(null)){
            throw new Exception("商品不能为空");
        }
        commodityService.add(commodity);
        return ResultVOUtil.success(commodity);
    }
}
