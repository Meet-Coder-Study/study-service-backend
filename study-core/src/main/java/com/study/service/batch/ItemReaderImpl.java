package com.study.service.batch;

import com.study.service.review.Review;
import com.study.service.user.User;
import com.study.service.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ItemReaderImpl implements ItemReader<List<Review>> {

    private final UserRepository userRepository;

    public ItemReaderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> read() {
        List<User> allUsers = userRepository.findAll();
        log.info("Read all users : {}", allUsers);
        return Review.matchReview(allUsers);
    }
}
