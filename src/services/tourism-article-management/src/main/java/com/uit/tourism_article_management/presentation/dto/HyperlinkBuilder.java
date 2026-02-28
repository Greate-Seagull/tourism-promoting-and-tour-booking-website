package com.uit.tourism_article_management.presentation.dto;

public class HyperlinkBuilder {
    private String href = "http://localhost:8080";
    private String method;
    private String expireIn = null;

    private HyperlinkBuilder(String method){
        this.method = method;
    }

    public static HyperlinkBuilder post(){
        return new HyperlinkBuilder("POST");
    }

    public static HyperlinkBuilder put(){
        return new HyperlinkBuilder("PUT");
    }

    public static HyperlinkBuilder delete(){
        return new HyperlinkBuilder("DELETE");
    }

    public static HyperlinkBuilder patch(){
        return new HyperlinkBuilder("PATCH");
    }

    public static HyperlinkBuilder get(){
        return new HyperlinkBuilder("GET");
    }

    public HyperlinkBuilder withHref(String href){
        this.href = href;
        return this;
    }

    public HyperlinkBuilder withExpireIn(int minutes){
        this.expireIn = minutes + " minutes";
        return this;
    }

    public Hyperlink build() {
        return new Hyperlink(href, method, expireIn);
    }
}
