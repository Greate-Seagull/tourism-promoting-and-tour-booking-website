package com.uit.tourism_article_management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class HyperlinkCollection {
    @JsonValue
    HashMap<String, Hyperlink> links = new HashMap<>();

    public void addLink(String name, Hyperlink link) {
        links.put(name, link);
    }
}
