package com.uit.tourism_article_management.application.port.media;

import java.io.IOException;
import java.io.InputStream;

public interface MediaProcessor {
    <T> T process(InputStream origin, MediaConsumer<T> consumer) throws IOException;

    @FunctionalInterface
    interface MediaConsumer<T> {
        T accept(InputStream buffered, String checksum) throws IOException;
    }
}
