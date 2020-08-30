package com.study.service.review;


import com.study.service.user.Role;
import com.study.service.user.SocialType;
import com.study.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewTest {

    private final int USER_LIST_SIZE = 500;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = getUserStub();
    }

    @Test
    public void 회원은_자신을_리뷰할_수_없다() {
        // given
        // when
        List<Review> reviews = Review.matchReview(users);
        List<Review> redundantResult = reviews
                                        .stream()
                                        .filter(u -> u.getReviewee().getEmail().equals(u.getReviewer().getEmail()))
                                        .collect(Collectors.toList());
        // then
        assertThat(redundantResult.size()).isEqualTo(0);
    }

    @Test
    public void 각_회원은_한명에_대해서만_리뷰를_한다() {
        // given
        // when
        List<Review> reviews = Review.matchReview(users);
        List<Review> reviewerListForUser1 = reviews
                                .stream().filter(u -> u.getReviewer().getEmail().equals(users.get(0).getEmail())).collect(Collectors.toList());
        // then
        assertThat(reviewerListForUser1.size()).isEqualTo(1);
    }

    @Test
    public void 회원리스트_널_예외_테스트() {
        assertThrows(NullPointerException.class, () -> Review.matchReview(null)).getMessage();
    }

    @Test
    public void 이전리스트와_같은_리뷰어를_선정할수없다() {
        // TODO
    }

    // user list stub
    private List<User> getUserStub() {

        List<User> list = new ArrayList<>(USER_LIST_SIZE);

        for (int i = 0; i < USER_LIST_SIZE; i++) {
            list.add(new User(String.format("test%s@posting.com", i),
                            String.format("member%s", i),
                            "prcpl",
                            Role.MEMBER,
                            SocialType.GITHUB));
        }

        return list;
    }

}