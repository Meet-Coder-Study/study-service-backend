package com.study.service.review;


import com.study.service.user.DeveloperType;
import com.study.service.user.Role;
import com.study.service.user.SocialType;
import com.study.service.user.User;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewTest {

    private final int BACKEND_USER_LIST_SIZE = 18;
    private final int FRONTEND_USER_LIST_SIZE = 17;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = getUserStub();
    }

    @Test
    public void usersCannotReviewThemSelves() {
        // given
        // when
        List<Review> reviews = Review.matchUsersByDevType(users);
        List<Review> redundantResult = reviews
                                        .stream()
                                        .filter(u -> u.getReviewee().getEmail().equals(u.getReviewer().getEmail()))
                                        .collect(Collectors.toList());
        // then
        System.out.println(reviews);
        assertThat(redundantResult.size()).isEqualTo(0);
    }

    @Test
    public void eachUserShouldReviewAtMostOneUser() {
        // given
        // when
        List<Review> reviews = Review.matchUsersByDevType(users);
        List<Review> reviewerListForUser1 = reviews
                                .stream().filter(u -> u.getReviewer().getEmail().equals(users.get(0).getEmail())).collect(Collectors.toList());
        // then
        assertThat(reviewerListForUser1.size()).isEqualTo(1);
    }

    @Test
    public void userListNullTest() {
        assertThrows(NullPointerException.class, () -> Review.matchUsersByDevType(null)).getMessage();
    }

    @Test
    public void whenUsersHaveADeveloperTypeIsOnlyOneDoNotMatch() {
        // given
        // when
        // then
    }

    @Ignore
    @Test
    public void 이전리스트와_같은_리뷰어를_선정할수없다() {
        // TODO
    }

    // user list stub
    private List<User> getUserStub() {

        List<User> list = new ArrayList<>(BACKEND_USER_LIST_SIZE + FRONTEND_USER_LIST_SIZE);


        for (int i = 0, backendUserIndex = 0; i < BACKEND_USER_LIST_SIZE + FRONTEND_USER_LIST_SIZE; i++, backendUserIndex++) {
            list.add(new User(String.format("test%s@posting.com", i),
                            String.format("member%s", i),
                            "prcpl",
                            Role.MEMBER,
                            SocialType.GITHUB,
                            backendUserIndex <= BACKEND_USER_LIST_SIZE ? DeveloperType.BACKEND : DeveloperType.FRONTEND));
        }


        return list;
    }

}