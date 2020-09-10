package com.study.service.batch;

import com.study.service.review.Review;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    //private final JobCompletionNotificationListener listener;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReaderImpl itemReader;

    private final ItemWriterImpl itemWriter;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReaderImpl itemReader, ItemWriterImpl itemWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.itemReader = itemReader;
        this.itemWriter = itemWriter;
    }

    //@Bean
    public Job importReviewJob(JobRepository jobRepository, Step step1) {
        return jobBuilderFactory.get("importReviewJob")
                .repository(jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("step1")
                .transactionManager(transactionManager)
                .<List<Review>, List<Review>> chunk(1)
                .reader(itemReader)
                .writer(itemWriter)
                .startLimit(1)
                .build();
    }

    @PostConstruct
    public void setup() {
        System.out.println("TEST********###########");
    }
}
