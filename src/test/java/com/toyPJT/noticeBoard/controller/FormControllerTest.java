package com.toyPJT.noticeBoard.controller;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FormController.class)
class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
        httpSession.setAttribute(SESSION_NAME, "name1");
    }

    @DisplayName("회원가입 폼을 받을 수 있다.")
    @Test
    void joinForm_get_success() throws Exception {
        mockMvc.perform(get("/joinForm"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/joinForm"));
    }

    @DisplayName("로그인 폼을 받을 수 있다.")
    @Test
    void loginForm_get_success() throws Exception {
        mockMvc.perform(get("/loginForm"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/loginForm"));
    }

    @DisplayName("세션이 존재한다면, 로그아웃 체크 폼을 받을 수 있다.")
    @Test
    void logoutForm_get_success() throws Exception {
        mockMvc.perform(get("/logoutCheck")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("user/logoutCheck"));
    }

    @DisplayName("세션이 존재한다면, 게시글 저장 폼을 받을 수 있다.")
    @Test
    void saveForm_get_success() throws Exception {
        mockMvc.perform(get("/saveForm")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("board/saveForm"));
    }
}
