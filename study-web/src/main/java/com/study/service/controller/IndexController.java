package com.study.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    // TODO: 2020/08/30 테스트를 위한 컨트롤러
    @GetMapping()
    public String index() {
        return "hello";
    }
}
