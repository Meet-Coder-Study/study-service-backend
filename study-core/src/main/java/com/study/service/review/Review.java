package com.study.service.review;

import com.study.service.common.BaseEntity;
import com.study.service.user.User;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reviewee;

    public Review(User reviewer, User reviewee) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
    }

    public static List<Review> matchReview(@NotNull List<User> users) {
        Collections.shuffle(users);

        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < users.size() - 1; i++) {
            reviews.add(new Review(users.get(i), users.get(i+1)));
        }

        // In order to get last user of the list reviewed,
        if(users.size() % 2 != 0) {
            reviews.add(new Review(users.get(users.size()-1), users.get(0)));
        }

        return reviews;
    }

}
