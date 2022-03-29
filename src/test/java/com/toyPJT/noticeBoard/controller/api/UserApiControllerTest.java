package com.toyPJT.noticeBoard.controller.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyPJT.noticeBoard.dto.UserSaveRequest;
import com.toyPJT.noticeBoard.service.UserService;

@WebMvcTest(controllers = UserApiController.class)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private MockHttpSession httpSession;
    private UserSaveRequest userSaveRequest;

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
        httpSession.setAttribute("loginMember", "userId");
        userSaveRequest = new UserSaveRequest("username", "1111", "lee@google.com");
    }

    @DisplayName("join 요청 시 정상적으로 가입되었으면 OK status를 반환한다.")
    @Test
    void join_success() throws Exception {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest("name1", "pwd1", "mail@mail");
        doNothing().when(userService).register(any(UserSaveRequest.class));

        //  when & then
        mockMvc.perform(
                post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userSaveRequest)))
            .andExpect(status().isCreated());
    }

    @DisplayName("update 요청 시 세션이 없을 경우 로그인 페이지로 redirect 된다.")
    @Test
    void update_fail_if_no_session() throws Exception {
        mockMvc.perform(
                put("/user/1"))
            .andExpect(header().string("Location", "/loginForm"))
            .andExpect(status().is3xxRedirection());
    }

    @DisplayName("update 요청 시 세션이 있을 경우 정상적으로 OK 상태를 반환한다.")
    @Test
    void update_success_if_no_session() throws Exception {
        mockMvc.perform(
                put("/user/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userSaveRequest))
                    .session(httpSession))
            .andExpect(status().isOk());
    }
}
