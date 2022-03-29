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
import com.toyPJT.noticeBoard.domain.reply.Reply;
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
    private Reply reply;

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
        httpSession.setAttribute(SESSION_NAME, "name1");

        board = Board.builder()
            .title("title1")
            .content("content1")
            .build();

        reply = Reply.builder()
            .content("reply Content1")
            .build();
    }

    @DisplayName("게시글 저장 요청 시 세션이 없으면 로그인 페이지로 Redirect된다.")
    @Test
    void board_request_fail() throws Exception {
        mockMvc.perform(post("/board"))
            .andExpect(header().string("Location", "/loginForm"))
            .andExpect(status().is3xxRedirection());
    }

    @DisplayName("게시글 저장 중 예외가 발생하지 않으면 정상적으로 저장된다.")
    @Test
    void create_success() throws Exception {
        mockMvc.perform(post("/board")
                .session(httpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(board)))
            .andExpect(status().isCreated());
    }

    @DisplayName("게시글 삭제 요청 시 세션이 없으면 로그인 페이지로 Redirect된다.")
    @Test
    void delete_request_fail() throws Exception {
        mockMvc.perform(delete("/board/1"))
            .andExpect(header().string("Location", "/loginForm"))
            .andExpect(status().is3xxRedirection());
    }

    @DisplayName("삭제 예외가 발생하지 않으면 정상적으로 삭제된다.")
    @Test
    void delete_success() throws Exception {
        mockMvc.perform(delete("/board/1")
                .session(httpSession))
            .andExpect(status().isOk());
    }

    @DisplayName("reply 추가 시 세션이 없으면 로그인 페이지로 Redirect 된다.")
    @Test
    void reply_save_fail() throws Exception {
        mockMvc.perform(post("/board/1/reply"))
            .andExpect(header().string("Location", "/loginForm"))
            .andExpect(status().is3xxRedirection());
    }

    @DisplayName("reply 추가 시 예외가 발생하지 않으면 정상적으로 등록된다.")
    @Test
    void reply_save_success() throws Exception {
        mockMvc.perform(post("/board/1/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reply))
                .session(httpSession))
            .andExpect(status().isCreated());
    }
}
