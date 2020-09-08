package com.study.service.batch;

import com.study.service.review.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@Slf4j
public class ItemProcessorImpl implements ItemProcessor<List<Review>, List<Review>> {

    @Override
    public List<Review> process(final List<Review> reviews) throws Exception {
        log.info("Intermediate processor Reviews");
        return reviews;
    }
}
