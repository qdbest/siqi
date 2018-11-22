package com.yucn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/11/22.
 */
@RestController
@RequestMapping("/hello")
public class TestController {
    @GetMapping
    public String hello(){
        return "hello";
    }

}
