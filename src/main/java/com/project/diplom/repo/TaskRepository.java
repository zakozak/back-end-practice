package com.project.diplom.repo;

import com.project.diplom.model.entity.Board;
import com.project.diplom.model.entity.Task;
import com.project.diplom.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findTaskById(Long taskId);

    List<Task> findAllByBoardId(Board boardId);
}
