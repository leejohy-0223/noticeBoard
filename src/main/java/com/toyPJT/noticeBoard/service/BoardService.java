package com.toyPJT.noticeBoard.service;

import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.domain.board.BoardRepository;
import com.toyPJT.noticeBoard.domain.reply.Reply;
import com.toyPJT.noticeBoard.domain.reply.ReplyRepository;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.dto.BoardDetailResponse;
import com.toyPJT.noticeBoard.dto.BoardSummaryResponse;
import com.toyPJT.noticeBoard.dto.BoardTemplateRequest;
import com.toyPJT.noticeBoard.dto.ReplySaveRequest;
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public void save(BoardTemplateRequest boardSaveRequest, User user) {
        Board board = Board.builder()
            .user(user)
            .title(boardSaveRequest.getTitle())
            .content(boardSaveRequest.getContent())
            .count(0)
            .build();
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardSummaryResponse> getBoards(Pageable pageable) {
        return boardRepository.findBoardSummary(pageable);
    }

    public BoardDetailResponse getBoardDetails(Integer id) {
        Board board = getBoard(id);
        board.increaseCount();
        return new BoardDetailResponse(board.getTitle(), board.findUsername(), board.getId(), board.getCount(), board.getContent(), board.getReplies());
    }

    public void delete(Integer id) {
        boardRepository.deleteById(id);
    }

    public void update(Integer id, BoardTemplateRequest request) {
        Board board = getBoard(id);
        board.updateValue(request.getTitle(), request.getContent());
    }

    public void saveReply(ReplySaveRequest request, Integer boardId, User user) {
        Board board = getBoard(boardId);
        Reply reply = Reply.builder()
            .board(board)
            .content(request.getContent())
            .user(user)
            .build();

        replyRepository.save(reply);
    }

    public void deleteReply(Integer replyId) {
        replyRepository.deleteById(replyId);
    }

    public boolean checkBoardWriterMatches(Integer boardId, String loginMemberName) {
        Board board = getBoard(boardId);
        return board.getUser().isYourName(loginMemberName);
    }

    public boolean checkReplyWriterMatches(Integer replyId, String loginMemberName) {
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new GlobalException(REPLY_DOES_NOT_EXIST));
        return reply.getUser().isYourName(loginMemberName);
    }

    private Board getBoard(Integer id) {
        return boardRepository.findById(id)
            .orElseThrow(() -> new GlobalException(BOARD_DOES_NOT_EXIST));
    }
}
