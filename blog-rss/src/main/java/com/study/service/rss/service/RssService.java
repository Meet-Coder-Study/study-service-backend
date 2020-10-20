package com.study.service.rss.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.study.service.rss.dto.Feed;
import com.study.service.rss.reader.RssFeedReader;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RssService {
    private final RssFeedReader rssFeedReader;

    public List<Feed> getFeed(final String rssUrl) {
        final SyndFeed syndFeed = rssFeedReader.readFeed(rssUrl);
        final List<SyndEntry> entries = syndFeed.getEntries();

        return entries.stream()
                .map(feed -> new Feed(feed.getTitle(), feed.getAuthor(),
                        feed.getDescription().getValue().replaceAll("\\<[^>]*>", ""),
                        feed.getLink(),
                        LocalDateTime.ofInstant(feed.getPublishedDate().toInstant(), ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }
}
