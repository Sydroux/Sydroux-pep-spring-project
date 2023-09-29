package com.example.repository;

import com.example.entity.Message;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("FROM Message WHERE message_id = :varId")
    Optional<Message> findMessageByMessageId (@Param("varId") int message_id);

    @Modifying
    @Query("DELETE FROM Message WHERE message_id = :varId")
    int deleteMessageByMessageId (@Param("varId") int message_id);

    @Modifying
    @Query("UPDATE Message SET message_text = :varText WHERE message_id = :varId")
    int updateMessageByMessageId (@Param("varText") String message_text, @Param("varId") int message_id);

    @Query("FROM Message WHERE posted_by = :varId")
    Optional<List<Message>> findMessagesByAccountId (@Param("varId") int id);
}
