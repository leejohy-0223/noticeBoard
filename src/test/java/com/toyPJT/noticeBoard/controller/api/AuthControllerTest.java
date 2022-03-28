package com.toyPJT.noticeBoard.controller.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyPJT.noticeBoard.dto.LoginRequest;
import com.toyPJT.noticeBoard.exception.ExceptionType;
import com.toyPJT.noticeBoard.exception.GlobalException;
import com.toyPJT.noticeBoard.service.UserService;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("userId1", "1111");
    }

    @DisplayName("로그인 요청이 정상적으로 동작할 경우, Status OK 코드를 반환한다.")
    @Test
    void login_success() throws Exception {
        // given
        given(userService.login(loginRequest))
            .willReturn("userId1");

        // when
        ResultActions perform = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        perform.andExpect(status().isOk());
    }

    @DisplayName("로그인 요청이 비정상적으로 동작할 경우, 예외가 발생한다.")
    @Test
    void login_fail() throws Exception {
        // given
        given(userService.login(loginRequest))
            .willThrow(new GlobalException(ExceptionType.USER_NAME_DOES_NOT_EXIST));

        // when
        ResultActions perform = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        perform.andExpect(status().is4xxClientError());
    }

    @DisplayName("로그아웃이 정상 동작한다.")
    @Test
    void logout_success() throws Exception {
        // given
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("loginMember", "userId");

        // when
        ResultActions perform = mockMvc.perform(get("/logout")
            .session(httpSession));

        // then
        perform.andExpect(status().is3xxRedirection());
        assertThat(httpSession.isInvalid()).isTrue();
    }
}
