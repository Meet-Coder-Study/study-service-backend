package com.study.service.rss.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Feed {
    private final String title;
    private final String author;
    private final String description;
    private final String link;
    private final LocalDateTime createdTime;
}
