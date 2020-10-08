package com.study.service.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.study.service.user.Role;
import com.study.service.user.SocialType;
import com.study.service.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {
    private final Long id;

    private final String email;

    private final String name;

    private final String principal;

    private final Role role;

    private final SocialType socialType;

    public static UserDto of(final User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getPrincipal(), user.getRole(),
                user.getSocialType());
    }

    public static List<UserDto> ofList(final List<User> users) {
        return users.stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }
}
