package com.study.service.blog;

import java.net.URI;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Feed {

    @Id
    @Column(name = "feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String description;
    @Column
    private URI link;
    @Column
    private LocalDateTime createdTime;

    @Builder
    public Feed(
            final Long id, final String title, final String author, final String description, final URI link,
            final LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.link = link;
        this.createdTime = createdTime;
    }
}
