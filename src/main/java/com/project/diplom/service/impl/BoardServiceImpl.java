package com.project.diplom.service.impl;

import com.project.diplom.exception.DiplomaException;
import com.project.diplom.exception.ExceptionCode;
import com.project.diplom.model.dto.BoardDto;
import com.project.diplom.model.dto.BoardViewDto;
import com.project.diplom.model.entity.Board;
import com.project.diplom.model.entity.User;
import com.project.diplom.repo.BoardRepository;
import com.project.diplom.repo.UserRepository;
import com.project.diplom.service.BoardService;
import com.project.diplom.shared.BoardMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BoardViewDto getBoard(Long boardId, Long userId) {
        Board board = boardRepository.findBoardById(boardId);
        if (!board.getOwner().getId().equals(userId)
                && board.getUsers().stream().map(User::getId).noneMatch(id -> id.equals(userId))) {
            throw new DiplomaException(
                    String.format("User with id: %s, does not have access to the the board with id: %s", userId, boardId),
                    ExceptionCode.NO_PERMISSION);
        }
        return BoardMapper.mapBoardViewDto(board);
    }

    @Override
    public List<BoardViewDto> getBoards(Long userId) {
        return boardRepository.findBoardsByOwnerAndUsersIn(userId)
                .stream()
                .map(BoardMapper::mapBoardViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createBoard(BoardDto boardDto, Long userId) {
        Board board = new Board();
        BeanUtils.copyProperties(boardDto, board);
        board.setCreationTime(new Date());

        List<User> futureBoardUsers = userRepository.findAllById(boardDto.getUserIds());
        board.setUsers(new HashSet<>());
        board.getUsers().addAll(futureBoardUsers);

        User owner = userRepository.findById(userId).orElseThrow(AssertionError::new);
        board.setOwner(owner);

        boardRepository.save(board);
    }

    @Override
    public void updateBoard(BoardDto boardDto, Long boardId, Long userId) {
        Board boardEntity = boardRepository.findBoardById(boardId);
        if (!userId.equals(boardEntity.getOwner().getId())) {
            throw new DiplomaException(
                    String.format("User with id: %s, is not owner of the board with id: %s", userId, boardId),
                    ExceptionCode.NO_PERMISSION);
        }
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setDescription(boardDto.getDescription());
        List<User> newBoardUsers = userRepository.findAllById(boardDto.getUserIds());
        boardEntity.setUsers(new HashSet<>());
        boardEntity.getUsers().addAll(newBoardUsers);
        boardRepository.save(boardEntity);
    }

    @Override
    public void deleteBoard(Long boardId, Long userId) {
        Board boardEntity = boardRepository.findBoardById(boardId);
        if (!userId.equals(boardEntity.getOwner().getId())) {
            throw new DiplomaException(
                    String.format("User with id: %s, is not owner of the board with id: %s", userId, boardId),
                    ExceptionCode.NO_PERMISSION);
        }
        boardRepository.delete(boardEntity);
    }
}
