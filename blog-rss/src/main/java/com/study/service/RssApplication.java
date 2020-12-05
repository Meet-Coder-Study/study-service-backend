package com.study.service;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class RssApplication {
    public static void main(final String[] args) {
        SpringApplication.run(RssApplication.class, args);
    }
}
