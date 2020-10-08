package com.study.service.batch;

import com.study.service.review.Review;
import com.study.service.review.ReviewHistory;
import com.study.service.review.ReviewHistoryRepository;
import com.study.service.review.ReviewRepository;
import com.study.service.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ReviewMatchTasklet implements Tasklet {

    private AtomicInteger step = new AtomicInteger();
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewHistoryRepository;

    public ReviewMatchTasklet(UserRepository userRepository, ReviewRepository reviewRepository, ReviewHistoryRepository reviewHistoryRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.reviewHistoryRepository = reviewHistoryRepository;
    }


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("STEP '{}' executed", step.getAndIncrement());

        List<Review> reviews = Review.matchUsersByDevType(userRepository.findAll());

        reviewRepository.saveAll(reviews);

        reviewHistoryRepository.save(ReviewHistory.from(reviews));

        // TODO send Slack API
        return RepeatStatus.FINISHED;
    }

}
