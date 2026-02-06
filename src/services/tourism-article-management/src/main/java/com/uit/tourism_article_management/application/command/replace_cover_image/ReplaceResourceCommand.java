package com.uit.tourism_article_management.application.command.replace_cover_image;

import java.io.InputStream;

public record ReplaceResourceCommand(String mediaId, String mimeType, InputStream stream) {
}
