package com.uit.tourism_article_management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Hyperlink {
    private final String href;
    private final String method;
    private final String expireIn;

    public Hyperlink(String href, String method, String expireIn) {
        this.href = href;
        this.method = method;
        this.expireIn = expireIn;
    }
}
