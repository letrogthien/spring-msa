package com.letrogthien.auth.repositories;

import com.letrogthien.auth.entities.SendMessageError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageErrorRepository extends JpaRepository<SendMessageError, UUID> {
}
