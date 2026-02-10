package com.uit.tourism_article_management.application.command.sync.upload_resource;

import java.io.InputStream;

public record UploadResourceCommand(String mediaId, String filename, String mimeType, InputStream stream) {
}
