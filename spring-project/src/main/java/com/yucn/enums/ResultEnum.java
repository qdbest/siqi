package com.yucn.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/1/12.
 */
@Getter
public enum ResultEnum {
    DATABASE_ERROR(10,"操作数据库失败"),
    CODE_EXIST(11,"条码已存在"),
    COMMODITY_NOT_EXIST(12, "商品不存在"),
    STOCK_IS_LOW(13,"库存不足")
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
