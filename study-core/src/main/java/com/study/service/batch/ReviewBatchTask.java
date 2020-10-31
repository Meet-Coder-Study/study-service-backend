package com.study.service.batch;

import com.study.service.review.Review;
import com.study.service.user.User;
import com.study.service.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

@Slf4j
public class ReviewBatchTask implements Tasklet {
    
    private final UserRepository userRepository;

    public ReviewBatchTask(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Review> reviews = read();

        // TODO 1. send slack api
        //      2. save to repository
        return RepeatStatus.FINISHED;
    }
    
    private List<Review> read() {
        List<User> allUsers = userRepository.findAll();
        log.info("Read all users : {}", allUsers);
        return Review.matchUsersByDevType(allUsers);
    }
}
