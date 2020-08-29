package com.study.service.review;

import com.study.service.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Getter
public class Review {

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

    private LocalDateTime reviewDataAt;

    public Review(User reviewer, User reviewee, LocalDateTime reviewDataAt) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.reviewDataAt = reviewDataAt;
    }

    public static List<Review> matchReview(List<User> users) {
        Objects.requireNonNull(users, "회원 리스트는 Null이 될 수 없습니다.");

        Collections.shuffle(users);

        List<Review> reviews = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < users.size() - 1; i++) {
            reviews.add(new Review(users.get(i), users.get(i+1), now));
        }

        // In order to get last user of the list reviewed,
        if(users.size() % 2 != 0) {
            reviews.add(new Review(users.get(users.size()-1), users.get(0), now));
        }

        return reviews;
    }

}
