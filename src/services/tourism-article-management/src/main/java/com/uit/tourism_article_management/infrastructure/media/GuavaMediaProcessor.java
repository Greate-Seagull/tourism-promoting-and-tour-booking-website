package com.uit.tourism_article_management.infrastructure.media;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.common.io.ByteStreams;
import com.google.common.io.FileBackedOutputStream;
import com.uit.tourism_article_management.application.port.media.MediaProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class GuavaMediaProcessor implements MediaProcessor {
    private static final int FILE_THRESHOLD = 5 * 1024 * 1024;

    @Override
    public <T> T process(InputStream origin, MediaConsumer<T> consumer) throws IOException {
        try (var buffer = new FileBackedOutputStream(FILE_THRESHOLD, true)) {
            String checksum;
            try (var hashingOut = new HashingOutputStream(Hashing.sha256(), buffer)){
                ByteStreams.copy(origin, hashingOut);
                checksum = hashingOut.hash().toString();
            }
            try (var content = buffer.asByteSource().openStream()){
                return consumer.accept(content, checksum);
            }
        }
    }
}
