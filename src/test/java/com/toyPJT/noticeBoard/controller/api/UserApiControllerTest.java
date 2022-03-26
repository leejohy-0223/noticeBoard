package com.toyPJT.noticeBoard.controller.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @DisplayName("join 요청 시 정상적으로 가입되었으면 OK status를 반환한다.")
    @Test
    void join_success() throws Exception {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest("name1", "pwd1", "mail@mail");
        doNothing().when(userService).register(any(UserSaveRequest.class));

        //  when & then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userSaveRequest)))
            .andExpect(status().isCreated());
    }
}
