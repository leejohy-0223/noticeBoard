package com.toyPJT.noticeBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FormController.class)
class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 폼을 받을 수 있다.")
    @Test
    void loginForm_get_success() throws Exception {
        mockMvc.perform(get("/joinForm"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/joinForm"));
    }
}
