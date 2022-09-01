package com.toyPJT.noticeBoard.domain.board;

import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.dto.BoardDetailResponse;
import com.toyPJT.noticeBoard.dto.BoardSummaryResponse;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT new com.toyPJT.noticeBoard.dto.BoardSummaryResponse(b.title, b.user.username, b.id, b.count) FROM Board b"
        + " ORDER BY b.id")
    Page<BoardSummaryResponse> findBoardSummary(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "replies"})
    Optional<Board> findById(Integer id);
}
