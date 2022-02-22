package com.project.diplom.service;


import com.project.diplom.model.dto.BoardDto;
import com.project.diplom.model.dto.BoardViewDto;

import java.util.List;

public interface BoardService {
    BoardViewDto getBoard(Long boardId, Long id);

    void createBoard(BoardDto board, Long userId);

    List<BoardViewDto> getBoards(Long userId);

    void updateBoard(BoardDto board, Long boardId, Long userId);

    void deleteBoard(Long boardId, Long userId);
}
