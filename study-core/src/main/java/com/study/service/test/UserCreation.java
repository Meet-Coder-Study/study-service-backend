package com.study.service.test;

import com.study.service.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 리뷰 매칭 스케쥴링 테스트 목적 유저 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreation implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = userRepository.findAll();

        if(users.size() > 0)
            return; // 이미 생성되어 있음

        // 새로 생성함
        List<User> savedUsers = userRepository.saveAll(getTestUsers());

        boolean success = (savedUsers != null) && (savedUsers.size() == getTestUsers().size());

        log.info("Test User creation '{}' ", success ? "SUCCESS" : "FAILED");
    }

    private List<User> getTestUsers() {
        return Arrays.asList(
                new User("a@bcd.com", "김세윤", "principal", Role.ADMIN, SocialType.GITHUB, DeveloperType.BACKEND),
                new User("b@bcd.com", "박창원", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND),
                new User("c@bcd.com", "이덕희", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.FRONTEND),
                new User("d@bcd.com", "최락준", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND),
                new User("e@bcd.com", "최소현", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.FRONTEND),
                new User("f@bcd.com", "박경철", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND),
                new User("g@bcd.com", "김혜인", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND),
                new User("h@bcd.com", "김나은", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.FRONTEND),
                new User("i@bcd.com", "석태진", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND),
                new User("j@bcd.com", "최성익", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.FRONTEND),
                new User("k@bcd.com", "최준성", "principal", Role.MEMBER, SocialType.GITHUB, DeveloperType.FRONTEND)
        );
    }
}
