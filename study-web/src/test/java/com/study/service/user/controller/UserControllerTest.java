package com.study.service.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.study.service.user.Role;
import com.study.service.user.SocialType;
import com.study.service.user.UserRepository;
import com.study.service.user.dto.UserDto;
import com.study.service.user.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("전체 유저를 가져오는 테스트")
    @Test
    void findUsersTest() throws Exception {
        given(userService.findUsers()).willReturn(
                Collections.singletonList(
                        new UserDto(1L, "test@gmail.com", "test", "test", Role.MEMBER, SocialType.GITHUB)));

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(1L))
                .andDo(print());
    }

    @DisplayName("전체 유저 권한를 가져오는 테스트")
    @Test
    void findRolesTest() throws Exception {
        mockMvc.perform(get("/api/v1/users/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]").value(Role.ADMIN.name()))
                .andExpect(jsonPath("[1]").value(Role.MEMBER.name()))
                .andDo(print());
    }
}
