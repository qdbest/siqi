### 后端controller接收多个对象的情况
- 使用map，通过json进行对象转换，遇到Collect，需要对内部元素再进行转换
```
@PostMapping("/addMany")
    public ResultVO<String> addMany(@RequestBody Map mapData) throws Exception {
        //将map转换为对象
        PurchaseOrder purchaseOrder = JSONObject.parseObject(JSONObject.toJSONString(mapData.get("purchaseOrder")), PurchaseOrder.class);
        List<StockCommodity> stockCommodities = JSONObject.parseObject(JSONObject.toJSONString(mapData.get("stockCommodities")), List.class);

        stockCommodityService.addMany(purchaseOrder, stockCommodities);

        return ResultVOUtil.success("提交成功");
    }
```
```
@Override
    @Transactional
    public void addMany(PurchaseOrder purchaseOrder, Set<StockCommodity> stockCommodities) throws Exception {
        List<StockCommodity> stockCommodityList=new ArrayList<>();
        for(Object o:stockCommodities){
            StockCommodity stockCommodity=JSONObject.parseObject(JSONObject.toJSONString(o),StockCommodity.class);
            stockCommodity.setPurchaseOrder(purchaseOrder);

            stockCommodity.setCommodity(commodityRepository.getOne(stockCommodity.getCommodity().getId()));

            stockCommodityList.add(stockCommodity);
        }
        try {
            stockCommodityRepository.saveAll(stockCommodities);
        } catch (Exception e) {
            throw new Exception("插入数据库错误，请检查是否填写完整！");
        }
    }
```