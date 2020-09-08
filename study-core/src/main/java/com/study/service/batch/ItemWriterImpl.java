package com.study.service.batch;

import com.study.service.review.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ItemWriterImpl implements ItemWriter<List<Review>> {


    @Override
    public void write(List<? extends List<Review>> items) throws Exception {
        log.info("Write");
    }
}
