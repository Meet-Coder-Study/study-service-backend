package com.study.service.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.service.user.Role;
import com.study.service.user.dto.UserDto;
import com.study.service.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers() {
        final List<UserDto> users = userService.findUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/roles")
    public ResponseEntity<Role[]> findRoles() {
        final Role[] roles = Role.values();

        return ResponseEntity.ok(roles);
    }
}
