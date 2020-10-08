package com.study.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EntityScan("com.study.service")
@SpringBootApplication(scanBasePackages = "com.study.service")
public class StudyServiceApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(StudyServiceApplication.class)
                .run(args);
    }
}

