package com.study.service.user.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.service.user.User;
import com.study.service.user.UserRepository;
import com.study.service.user.dto.UserDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("모든 유저를 조회해서 리턴하는지에 대한 테스트")
    @Test
    void findUsersTest() {

        userRepository.save(
                User.builder()
                        .email("test@gmail.com")
                        .build());

        final List<UserDto> users = userService.findUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("test@gmail.com");
    }
}
