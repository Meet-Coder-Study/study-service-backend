package com.study.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StudyServiceApplication {
    private static final String PROPERTIES = "spring.config.location="
            + "classpath:/config/application-oauth2.yml";

    public static void main(final String[] args) {
        new SpringApplicationBuilder(StudyServiceApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }
}
