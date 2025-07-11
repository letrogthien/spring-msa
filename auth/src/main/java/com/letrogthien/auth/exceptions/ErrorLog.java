package com.letrogthien.auth.exceptions;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("error_logs")
public class ErrorLog {
    @Id
    private String id;
    private long timestamp;
    private String path;
    private String method;
    private String errorCode;
    private String errorMessage;
    private String exceptionType;
    private String stackTrace;
}