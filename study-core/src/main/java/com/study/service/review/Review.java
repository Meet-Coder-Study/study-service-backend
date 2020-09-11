package com.study.service.review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.study.service.common.BaseEntity;
import com.study.service.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer", referencedColumnName = "user_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee", referencedColumnName = "user_id")
    private User reviewee;

    public Review(final User reviewer, final User reviewee) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
    }

    public static List<Review> matchReview(@NotNull final List<User> users) {
        Collections.shuffle(users);

        final List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < users.size() - 1; i++) {
            reviews.add(new Review(users.get(i), users.get(i + 1)));
        }

        // In order to get last user of the list reviewed,
        if (users.size() % 2 != 0) {
            reviews.add(new Review(users.get(users.size() - 1), users.get(0)));
        }

        return reviews;
    }

}
