package com.letrogthien.auth.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private String message;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @Builder.Default
    private HttpStatus status = HttpStatus.OK;
}
