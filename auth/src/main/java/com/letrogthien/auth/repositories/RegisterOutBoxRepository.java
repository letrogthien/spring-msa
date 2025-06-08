package com.letrogthien.auth.repositories;

import com.letrogthien.auth.common.Status;
import com.letrogthien.auth.entities.RegisterOutBox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegisterOutBoxRepository extends MongoRepository<RegisterOutBox, UUID> {
    List<RegisterOutBox> findAllByStatusOrderByCreatedAtAsc(Status status);
}
