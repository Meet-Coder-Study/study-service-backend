package com.study.service.test;

import com.study.service.review.ReviewRepository;
import com.study.service.user.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class InitializationDml implements ApplicationRunner {

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    public InitializationDml(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initUser();
    }

    private void initUser() {
        long count = userRepository.count();

        if(count > 0)
            return;

        userRepository.save(new User("abc@naver.com", "name-a", "principal-a", Role.ADMIN, SocialType.GITHUB, DeveloperType.FRONTEND));
        userRepository.save(new User("bcd@naver.com", "name-b", "principal-b", Role.MEMBER, SocialType.GITHUB,DeveloperType.FRONTEND));
        userRepository.save(new User("cde@naver.com", "name-c", "principal-c", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND));
        userRepository.save(new User("def@naver.com", "name-d", "principal-d", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND));
        userRepository.save(new User("efg@naver.com", "name-e", "principal-e", Role.MEMBER, SocialType.GITHUB, DeveloperType.BACKEND));
    }
}
