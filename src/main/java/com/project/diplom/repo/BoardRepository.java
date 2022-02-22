package com.project.diplom.repo;

import com.project.diplom.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findBoardById(Long id);

    @Query(value = "SELECT DISTINCT b.* from board b left join board_user bu on b.id = bu.board_id where b.user_id = :userId or bu.user_id = :userId", nativeQuery = true)
    List<Board> findBoardsByOwnerAndUsersIn(@Param("userId") Long userId);
}
