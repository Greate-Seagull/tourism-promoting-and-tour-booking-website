package com.uit.tourism_article_management.application.command.sync.media.upload_resource;

import java.io.InputStream;

public record UploadResourceCommand(String mimeType, InputStream stream) {
}
