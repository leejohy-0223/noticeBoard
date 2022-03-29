package com.toyPJT.noticeBoard.controller;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;
import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.toyPJT.noticeBoard.exception.GlobalException;
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
        log.debug("GET /");
        model.addAttribute("boards", boardService.getBoards(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        log.debug("GET /board/{}", id);
        model.addAttribute("board", boardService.getBoard(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateBoard(@PathVariable("id") Integer id, Model model, HttpSession session) {
        log.debug("GET /board/{}/updateForm", id);
        String loginMemberName = (String)session.getAttribute(SESSION_NAME);
        if (!boardService.checkBoardWriterMatches(id, loginMemberName)) {
            throw new GlobalException(NOT_A_WRITER);
        }
        model.addAttribute("board", boardService.getBoard(id));
        return "board/updateForm";
    }

    // TODO
    // 5. 카테고리 기능, 조회수 기능 등 추가.
    // 자신의 글 & 댓글 & 프로필만 수정, 삭제할 수 있도록 적용
    // [v] 글 수정
    // [v] 글 삭제
    // [v] 댓글 삭제
    // [] 프로필 수정
    // 자신의 정보를 계속 출력하도록 적용

}
