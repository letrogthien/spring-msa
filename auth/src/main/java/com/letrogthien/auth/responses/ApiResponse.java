package com.letrogthien.auth.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private String message;
    private LocalDateTime timestamp= LocalDateTime.now();
}
