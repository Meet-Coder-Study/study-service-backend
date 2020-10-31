package com.study.service.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private static final String JOB_NAME = "reviewMatchingJob";
    private static final String JOB_STEP1 = "step1";

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ReviewMatchTasklet reviewMatchTasklet;

    @Bean
    public Job reviewMatchingJob(JobRepository jobRepository, Step step1) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get(JOB_STEP1)
                .tasklet(reviewMatchTasklet)
                .exceptionHandler(new ExceptionHandlerImpl())
                .allowStartIfComplete(true)
                .build();
    }

    static class ExceptionHandlerImpl implements ExceptionHandler {
        @Override
        public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
            throw new RuntimeException("잡 실행이 실패하였습니다. " + throwable.getLocalizedMessage());
        }
    }
}
