package com.study.service.review;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
public class ReviewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="review_history_id")
    private Long id;

    @Column
    private LocalDateTime reviewDate;

    @Column
    private Integer reviewsCount;

    public ReviewHistory() {
    }

    public ReviewHistory(LocalDateTime reviewDate, Integer reviewsCount) {
        this.reviewDate = reviewDate;
        this.reviewsCount = reviewsCount;
    }

    public static ReviewHistory from(List<Review> reviews) {
        return new ReviewHistory(LocalDateTime.now(), reviews.size());
    }
}
