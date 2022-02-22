package com.project.diplom.service.impl;

import com.project.diplom.exception.DiplomaException;
import com.project.diplom.exception.ExceptionCode;
import com.project.diplom.model.dto.TaskDto;
import com.project.diplom.model.dto.TaskViewDto;
import com.project.diplom.model.entity.Board;
import com.project.diplom.model.entity.Task;
import com.project.diplom.model.entity.User;
import com.project.diplom.repo.BoardRepository;
import com.project.diplom.repo.TaskRepository;
import com.project.diplom.repo.UserRepository;
import com.project.diplom.service.TaskService;
import com.project.diplom.shared.TaskMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, BoardRepository boardRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskViewDto> getTasks(Long boardId, Long userId) {
        Board board = boardRepository.getOne(boardId);
        if (!board.getOwner().getId().equals(userId)
                && board.getUsers().stream().map(User::getId).noneMatch(id -> id.equals(userId))) {
            throw new DiplomaException(
                    "User can't get tasks for the border he/she doesn't have access to",
                    ExceptionCode.NO_PERMISSION);
        }

        return taskRepository.findAllByBoardId(boardRepository.findBoardById(boardId))
                .stream()
                .map(TaskMapper::mapTaskViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createTask(TaskDto taskDto, Long userId, Long boardId) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        task.setId(null);
        task.setDate(new Date());
        task.setTaskStatus(Task.TaskStatus.TODO);

        Board board = boardRepository.findBoardById(boardId);
        if (!userId.equals(board.getOwner().getId())
                && board.getUsers().stream().map(User::getId).noneMatch(id -> id.equals(userId))) {
            throw new DiplomaException(
                    "User can't create tasks for the border he/she doesn't have access to",
                    ExceptionCode.NO_PERMISSION);
        }

        task.setBoardId(board);
        task.setAssigneeId(userRepository.findUserById(taskDto.getAssigneeId()));

        taskRepository.save(task);
    }

    @Override
    public void updateTask(TaskDto taskDto, Long taskId, Long userId) {
        Task taskEntity = taskRepository.findTaskById(taskId);

        Board board = taskEntity.getBoardId();
        if (!userId.equals(board.getOwner().getId())
                && board.getUsers().stream().map(User::getId).noneMatch(id -> id.equals(userId))) {
            throw new DiplomaException(
                    "User can't update tasks for the border he/she doesn't have access to",
                    ExceptionCode.NO_PERMISSION);
        }

        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setTaskStatus(taskDto.getTaskStatus());
        taskRepository.save(taskEntity);
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        Task taskEntity = taskRepository.findTaskById(taskId);
        if (!userId.equals(taskEntity.getBoardId().getOwner().getId())) {
            throw new DiplomaException(
                    "User can't delete tasks for the border he/she isn't owner of",
                    ExceptionCode.NO_PERMISSION);
        }
        taskRepository.delete(taskEntity);
    }
}
