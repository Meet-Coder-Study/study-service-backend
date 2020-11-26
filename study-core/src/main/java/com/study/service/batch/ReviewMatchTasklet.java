package com.study.service.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.service.review.*;
import com.study.service.slack.service.SlackService;
import com.study.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ReviewMatchTasklet implements Tasklet {

    private AtomicInteger step = new AtomicInteger();
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewHistoryRepository;
    private final SlackService slackService;

    @Value("${slack.channel}")
    private String channelName;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("STEP '{}' executed", step.getAndIncrement());

        List<Review> reviews = Review.matchUsersByDevType(userRepository.findAll());

        reviewRepository.saveAll(reviews);

        reviewHistoryRepository.save(ReviewHistory.from(reviews));

        String sendResult = slackService.sendMessage(
                ReviewSlackMessage.makeReviewMatchingMessage(channelName, reviews));

        log.info("Call Slack API result : {}", sendResult);

        SlackService s = new SlackService();

        return RepeatStatus.FINISHED;
    }

}
