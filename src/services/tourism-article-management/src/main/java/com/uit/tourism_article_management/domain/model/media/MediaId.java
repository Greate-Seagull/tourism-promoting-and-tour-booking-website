package com.uit.tourism_article_management.domain.model.media;

import java.util.Objects;

public record MediaId(String id) {
    public static MediaId existing(String id) {
        if(id == null) return null;
        return new MediaId(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MediaId anotherId){
            return this.id.equals(anotherId.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
