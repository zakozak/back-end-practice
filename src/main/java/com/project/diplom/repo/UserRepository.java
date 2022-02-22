package com.project.diplom.repo;

import com.project.diplom.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findUserByName(String name);

    List<User> findByNameContainingIgnoreCase(String name);
}
