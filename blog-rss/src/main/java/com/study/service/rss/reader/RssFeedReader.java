package com.study.service.rss.reader;

import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Component
public class RssFeedReader {

    public SyndFeed readFeed(final String rssUrl) {
        try {
            final URL url = new URL(rssUrl);
            final SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(url));
        } catch (final FeedException | IOException e) {
            throw new IllegalArgumentException("해당 feed를 읽어오지 못했습니다.");
        }
    }
}
