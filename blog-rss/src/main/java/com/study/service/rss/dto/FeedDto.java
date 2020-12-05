package com.study.service.rss.dto;

import java.net.URI;
import java.time.LocalDateTime;

import com.study.service.blog.Feed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class FeedDto {
    private final String title;
    private final String author;
    private final String description;
    private final String link;
    private final LocalDateTime createdTime;

    public Feed toEntity() {
        return Feed.builder()
                .title(title)
                .author(author)
                .description(description)
                .link(URI.create(link))
                .createdTime(createdTime)
                .build();
    }
}
