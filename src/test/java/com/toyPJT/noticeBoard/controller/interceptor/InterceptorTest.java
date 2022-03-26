package com.toyPJT.noticeBoard.controller.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class InterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("loginMember", "userId");
    }

    @DisplayName("session이 있을 경우, 로그인 폼을 요청하면 redirect가 발생한다.")
    @Test
    void login_request_withSession_redirect() throws Exception {
        mockMvc.perform(get("/loginForm")
                .session(mockHttpSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/logoutCheck"));
    }

    @DisplayName("session이 있을 경우, 회원가입 폼을 요청하면 redirect가 발생한다.")
    @Test
    void join_requets_withSession_redirect() throws Exception {
        mockMvc.perform(get("/joinForm")
                .session(mockHttpSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/logoutCheck"));
    }
}
