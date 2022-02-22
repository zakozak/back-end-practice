package com.project.diplom.repo;

import com.project.diplom.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "select * from message where (dest_user_id = :destUserId and user_id = :userId) or (dest_user_id = :userId and user_id = :destUserId) order by creation_time desc", nativeQuery = true)
    List<Message> findAllByUserIdAndDestUserId(
            @Param("userId") Long userId,
            @Param("destUserId") Long destUserId);
}
