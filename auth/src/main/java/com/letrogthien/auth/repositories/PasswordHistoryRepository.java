package com.letrogthien.auth.repositories;

import java.util.List;
import java.util.UUID;

import com.letrogthien.auth.entities.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, UUID> {
    PasswordHistory findTopByUserIdOrderByCreatedAtDesc(UUID userId);

    @Query(
            value = "SELECT * FROM password_history WHERE user_id = :userId ORDER BY current_index DESC LIMIT 3",
            nativeQuery = true
    )
    List<PasswordHistory> getTop3(@Param("userId") UUID userId);
}
