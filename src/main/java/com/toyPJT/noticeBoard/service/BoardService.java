package com.toyPJT.noticeBoard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.domain.board.BoardRepository;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.exception.ExceptionType;
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void save(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getBoard(Integer id) {
        return boardRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ExceptionType.BOARD_DOES_NOT_EXIST));
    }

    @Transactional
    public void delete(Integer id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Integer id, Board updateBoard) {
        Board board = getBoard(id);
        board.updateValue(updateBoard);
    }
}
