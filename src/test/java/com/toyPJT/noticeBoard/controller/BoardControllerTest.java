package com.toyPJT.noticeBoard.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.toyPJT.noticeBoard.service.BoardService;

@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @DisplayName("기본 /요청에 대해서는 index view를 반환한다.")
    @Test
    void get_index() throws Exception {
        // given
        given(boardService.getBoards())
            .willReturn(new ArrayList<>());

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("boards"))
            .andExpect(view().name("index"));
    }

}
