package com.toyPJT.noticeBoard.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.toyPJT.noticeBoard.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model,
        @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.getBoards(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.getBoard(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateBoard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.getBoard(id));
        return "board/updateForm";
    }

    // TODO
    // 3. 회원정보 수정하기
    // 4. 댓글 랜더링
    // 5. 카테고리 기능, 조회수 기능 등 추가.

}
