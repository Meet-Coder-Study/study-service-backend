package com.study.service.review;

import com.study.service.common.BaseEntity;
import com.study.service.user.DeveloperType;
import com.study.service.user.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", name = "reviewer")
    private User reviewer;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", name = "reviewee")
    private User reviewee;

    public Review() {
    }

    public Review(User reviewer, User reviewee) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
    }

    public static List<Review> matchUsersByDevType(@NotNull List<User> users) {

        Map<DeveloperType, List<User>> userMap = generateUserMap(users);

        // separate into developer types
        users
            .stream()
            .forEach(u -> {
                List<User> userListByDevType = userMap.get(u.getDeveloperType());
                if(userListByDevType != null) userListByDevType.add(u);
            });


        return userMap
            .values()
            .stream()
            .map(userList -> matchUsers(userList))
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    private static Map<DeveloperType, List<User>> generateUserMap(List<User> users) {
        Map<DeveloperType, List<User>> maps = new HashMap<>(DeveloperType.values().length);

        // get developer types in users
        users
            .stream()
            .map(User::getDeveloperType)
            .collect(Collectors.toSet())
            .forEach(devType -> maps.put(devType, new ArrayList<>()));

        return maps;
    }

    private static List<Review> matchUsers(@NotNull List<User> users) {
        if(users.size() <= 1)
            throw new IllegalArgumentException("유저 1명만을 매칭할 수 없습니다.");

        Collections.shuffle(users);
        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < users.size() - 1; i++) {
            reviews.add(new Review(users.get(i), users.get(i+1)));
        }

        // In order to get last user of the list reviewed,
        if(users.size() % 2 != 0) {
            reviews.add(new Review(users.get(users.size() - 1), users.get(0)));
        }

        return reviews;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewer=" + reviewer +
                ", reviewee=" + reviewee +
                '}';
    }
}
