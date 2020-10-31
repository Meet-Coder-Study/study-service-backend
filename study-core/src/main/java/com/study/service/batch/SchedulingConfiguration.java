package com.study.service.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfiguration {

    private final JobLauncher jobLauncher;

    private final Job job;

    // Every Wednesday, 00:00
    @Scheduled(cron = "0 0 0 ? * WED")
    //@Scheduled(cron = "0/10 * * ? * *") // for test
    public void perform() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("timestamp", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        JobExecution e = jobLauncher.run(job, params);

        log.info("JOB END at {}", e.getEndTime().toString());
    }
}
