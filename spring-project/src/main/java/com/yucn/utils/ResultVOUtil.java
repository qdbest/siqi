package com.yucn.utils;

import com.yucn.vo.ResultVO;

/**
 * Created by Administrator on 2018/1/11.
 */
public class ResultVOUtil {
    public static ResultVO success(String msg, Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO error(String msg, Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

}
