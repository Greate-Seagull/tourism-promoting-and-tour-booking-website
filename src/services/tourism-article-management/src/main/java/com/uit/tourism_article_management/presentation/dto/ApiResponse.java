package com.uit.tourism_article_management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ApiResponse<T>{
    @Builder.Default
    private int code = 0;
    @Builder.Default
    private String message = "success";
    private T result;
}
