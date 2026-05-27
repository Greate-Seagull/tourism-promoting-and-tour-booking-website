package com.uit.tourism_article_management.domain.tour;

import java.util.List;

public class TourPresentation {
    private String title;
    private String introduction;
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public List<String> getImages() {
        return images;
    }

    public static TourPresentation create(String title, String introduction, List<String> images) {
        TourPresentation tourPresentation = new TourPresentation();
        tourPresentation.setTitle(title);
        tourPresentation.setIntroduction(introduction);
        tourPresentation.setImages(images);
        return tourPresentation;
    }

    private void setImages(List<String> images) {
        if (images == null || images.isEmpty())
            throw new RuntimeException("Images must be provided at least 1 image");
        this.images = images;
    }

    private void setIntroduction(String introduction) {
        if (introduction == null || introduction.isBlank())
            throw new RuntimeException("Introduction must be provided");
        this.introduction = introduction;
    }

    private void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new RuntimeException("Title must be provided");
        this.title = title;
    }
}
