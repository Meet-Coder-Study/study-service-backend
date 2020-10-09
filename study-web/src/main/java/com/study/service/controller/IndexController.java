package com.study.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.service.slack.service.SlackService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final SlackService slackService;

    @Value("${slack.token}")
    private String token;

    // TODO: 2020/08/30 테스트를 위한 컨트롤러
    @GetMapping()
    public String index() {
        return "String";
    }
}
