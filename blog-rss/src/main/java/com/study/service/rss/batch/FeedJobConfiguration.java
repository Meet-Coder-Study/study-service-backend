package com.study.service.rss.batch;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.service.blog.Blog;
import com.study.service.blog.Feed;
import com.study.service.blog.FeedRepository;
import com.study.service.rss.dto.FeedDto;
import com.study.service.rss.service.RssService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FeedJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RssService rssService;
    private final EntityManagerFactory entityManagerFactory;
    private final FeedRepository feedRepository;

    @Bean("feedJob")
    public Job feedBatchJob() {
        return jobBuilderFactory.get("feedJob")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(feedBatchStep())
                .build();
    }

    @Bean("feedStep")
    public Step feedBatchStep() {
        return stepBuilderFactory.get("feedStep")
                .<Blog, List<Feed>>chunk(10)
                .reader(feedReader())
                .processor(feedProcessor())
                .writer(feedWriter())
                .build();
    }

    @Bean("feedReader")
    public JpaPagingItemReader<Blog> feedReader() {
        return new JpaPagingItemReaderBuilder<Blog>()
                .pageSize(10)
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT blog FROM blog")
                .name("feedReader")
                .build();
    }

    @Bean("feedProcessor")
    public ItemProcessor<Blog, List<Feed>> feedProcessor() {
        return item -> {
            final List<FeedDto> feeds = rssService.getFeed(item.getLink());

            return feeds.stream()
                    .map(FeedDto::toEntity)
                    .collect(Collectors.toList());
        };
    }

    public ItemWriter<List<Feed>> feedWriter() {
        return items -> items.forEach(feedRepository::saveAll);
    }
}
