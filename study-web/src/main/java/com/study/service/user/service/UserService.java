package com.study.service.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.study.service.user.User;
import com.study.service.user.UserRepository;
import com.study.service.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> findUsers() {
        final List<User> users = userRepository.findAll();

        return UserDto.ofList(users);
    }
}
