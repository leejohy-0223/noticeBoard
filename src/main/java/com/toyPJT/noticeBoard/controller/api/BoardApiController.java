package com.toyPJT.noticeBoard.controller.api;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.service.BoardService;
import com.toyPJT.noticeBoard.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/board")
    public ResponseEntity<Void> save(@RequestBody Board board, HttpSession httpSession) {
        String userName = (String)httpSession.getAttribute(SESSION_NAME);
        User user = userService.findUser(userName);
        boardService.save(board, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
