package com.project.diplom.shared;

import com.project.diplom.model.dto.BoardViewDto;
import com.project.diplom.model.entity.Board;

import java.util.stream.Collectors;

public class BoardMapper {

    public static BoardViewDto mapBoardViewDto(Board board) {
        BoardViewDto boardViewDto = new BoardViewDto();
        boardViewDto.setId(board.getId());
        boardViewDto.setDescription(board.getDescription());
        boardViewDto.setTitle(board.getTitle());

        boardViewDto.setOwner(UserMapper.mapToUserFilteringDto(board.getOwner()));
        boardViewDto.setUsers(board.getUsers()
                .stream()
                .map(UserMapper::mapToUserFilteringDto)
                .collect(Collectors.toList()));

        return boardViewDto;
    }
}
