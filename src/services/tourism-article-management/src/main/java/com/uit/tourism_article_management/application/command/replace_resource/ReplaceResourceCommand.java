package com.uit.tourism_article_management.application.command.replace_resource;

import java.io.InputStream;

public record ReplaceResourceCommand(String mediaId, String mimeType, InputStream stream) {
}
