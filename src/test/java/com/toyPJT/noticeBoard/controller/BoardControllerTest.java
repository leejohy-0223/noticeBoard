package com.toyPJT.noticeBoard.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.exception.ExceptionType;
import com.toyPJT.noticeBoard.exception.GlobalException;
import com.toyPJT.noticeBoard.service.BoardService;


@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    private MockHttpSession httpSession;
    private Board board;
    private User user;

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
        httpSession.setAttribute("loginMember", "userId");
        user = User.builder()
            .username("user1")
            .build();
        board = Board.builder()
            .id(1)
            .content("content1")
            .title("title1")
            .user(user)
            .build();
    }

    @DisplayName("기본 /요청에 대해서는 index view를 반환한다.")
    @Test
    void get_index() throws Exception {
        // given
        given(boardService.getBoards(any(Pageable.class)))
            .willReturn(Page.empty());

        // when & then
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("boards"))
            .andExpect(view().name("index"));
    }

    @DisplayName("게시글의 id가 유효할 경우, 상세 내용 페이지를 반환한다.")
    @Test
    void get_board_success() throws Exception {
        // given
        given(boardService.getBoard(any()))
            .willReturn(board);

        mockMvc.perform(get("/board/1")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("board"))
            .andExpect(view().name("board/detail"));
    }

    @DisplayName("게시글의 id가 유효할 경우, 업데이트 폼을 반환한다.")
    @Test
    void get_update_form_success() throws Exception {
        // given
        given(boardService.checkBoardWriterMatches(1, "userId"))
            .willReturn(true);
        given(boardService.getBoard(1))
            .willReturn(board);

        mockMvc.perform(get("/board/1/updateForm")
                .session(httpSession))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("board"))
            .andExpect(view().name("board/updateForm"));
    }

    @DisplayName("게시글의 id가 유효하지 않을 경우, 에러페이지로 리다이렉트된다.")
    @Test
    void get_update_form_fail() throws Exception {
        // given
        given(boardService.getBoard(1))
            .willThrow(new GlobalException(ExceptionType.BOARD_DOES_NOT_EXIST));

        mockMvc.perform(get("/board/1/updateForm")
                .session(httpSession))
            .andExpect(status().is4xxClientError());
    }
}
