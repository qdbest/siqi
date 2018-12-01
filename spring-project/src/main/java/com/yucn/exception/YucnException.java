package com.yucn.exception;

import com.yucn.enums.ResultEnum;

/**
 * Created by Administrator on 2018/2/5.
 */
public class YucnException extends RuntimeException {
    private Integer code;

    public YucnException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public YucnException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
