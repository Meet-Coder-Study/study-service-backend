package com.study.service.slack.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MessageRequest {
    private final String channel;
    private final String text;
}
