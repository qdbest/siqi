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

/**
 * Created by Administrator on 2018/11/23.
 */
@RestController
@RequestMapping("/commodity")
@Slf4j
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @GetMapping("/currentPage/{currentPage}/pageSize/{pageSize}")
    public ResultVO list(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize) {
        log.info("查询商品列表");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest request = PageRequest.of(currentPage - 1, pageSize, sort);
        Page<Commodity> commodityPage = commodityService.list(request);
        System.out.println(commodityPage);
        return ResultVOUtil.success(null, commodityPage);
    }

    @GetMapping("/code/{code}")
    public ResultVO findByCode(@PathVariable String code) {
        log.info("根据条码查询商品是否已经存在");
        Commodity commodity = commodityService.findByCode(code);
        return ResultVOUtil.success(null, commodity);
    }

    @PostMapping("/add")
    public ResultVO add(@RequestBody Commodity commodity) throws Exception {
        log.info("添加商品");
        if (commodity.equals(null)) {
            throw new Exception("商品不能为空");
        }
        commodityService.add(commodity);
        return ResultVOUtil.success("添加商品成功", null);
    }

    @PutMapping
    public ResultVO update(@RequestBody Commodity commodity) {
        log.info("修改商品");
        commodity = commodityService.update(commodity);
        return ResultVOUtil.success("修改商品成功", commodity);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Long id) {
        log.info("删除商品");
        Commodity commodity = commodityService.delete(id);
        return ResultVOUtil.success("删除商品成功", commodity);
    }
}
