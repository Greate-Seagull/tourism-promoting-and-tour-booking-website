package com.uit.tourism_article_management.domain.model.media;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record MediaName(String name) {
    public static MediaName generateName(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = dtf.format(LocalDateTime.now());
        return new MediaName("upload" + timestamp);
    }

    public static MediaName existing(String filename) {
        return new MediaName(filename);
    }
}
