package com.toyPJT.noticeBoard.controller.api;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;
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
import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.service.BoardService;
import com.toyPJT.noticeBoard.service.UserService;

@WebMvcTest(controllers = BoardApiController.class)
class BoardApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @MockBean
    private UserService userService;

    private MockHttpSession httpSession;
    private Board board;

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
        httpSession.setAttribute(SESSION_NAME, "name1");

        board = Board.builder()
            .title("title1")
            .content("content1")
            .build();
    }

    @DisplayName("세션이 없으면 로그인 페이지로 Redirect된다.")
    @Test
    void board_request_fail() throws Exception {
        mockMvc.perform(post("/board"))
            .andExpect(header().string("Location", "/loginForm"))
            .andExpect(status().is3xxRedirection());
    }

    @DisplayName("저장 중 예외가 발생하지 않으면 CREATE가 정상적으로 동작한다.")
    @Test
    void create_success() throws Exception {
        mockMvc.perform(post("/board")
                .session(httpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(board)))
            .andExpect(status().isCreated());
    }
}
