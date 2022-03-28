package com.toyPJT.noticeBoard.controller;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @DisplayName("세션이 없는 상태에서 업데이트 폼을 요청 시 로그인 페이지로 이동한다.")
    @Test
    void update_form_fail() throws Exception {
        mockMvc.perform(get("/user/updateForm"))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/loginForm"));
    }

    @DisplayName("세션이 있다면 업데이트 폼으로 이동한다.")
    @Test
    void update_form_success() throws Exception {
        // given
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute(SESSION_NAME, "name1");
        User user = User.builder()
            .id(1)
            .username("name1")
            .email("lee@google.com")
            .build();

        given(userService.findUser("name1"))
            .willReturn(user);

        mockMvc.perform(get("/user/updateForm")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(view().name("user/updateForm"));
    }
}
