package com.wallet.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * ApiResponse - Generic API response wrapper
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private Integer statusCode;
    private T data;
    private LocalDateTime timestamp;
    private String path;
    private String errorCode;
    private java.util.List<String> errors;

    /**
     * Success response
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .statusCode(200)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Success response with custom status code
     */
    public static <T> ApiResponse<T> success(String message, T data, Integer statusCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .statusCode(statusCode)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Error response
     */
    public static <T> ApiResponse<T> error(String message, Integer statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .statusCode(statusCode)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Error response with error code
     */
    public static <T> ApiResponse<T> error(String message, String errorCode, Integer statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .statusCode(statusCode)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
