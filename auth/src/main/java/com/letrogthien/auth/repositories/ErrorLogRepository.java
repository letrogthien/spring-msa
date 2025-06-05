package com.letrogthien.auth.repositories;

import com.letrogthien.auth.exceptions.ErrorLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ErrorLogRepository extends MongoRepository<ErrorLog, String> {
    // Custom query methods can be defined here if needed
}
