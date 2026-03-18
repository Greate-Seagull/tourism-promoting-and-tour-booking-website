package com.uit.tourism_article_management.presentation.exception;

import com.uit.tourism_article_management.domain.model.DomainException;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse>handleDomain(DomainException exception){
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .code(exception.getCode())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse>handleRuntime(RuntimeException exception){
        return ResponseEntity.internalServerError()
                .body(ApiResponse.builder()
                        .code("Unknown")
                        .message(exception.getMessage())
                        .build()
                );
    }
}
