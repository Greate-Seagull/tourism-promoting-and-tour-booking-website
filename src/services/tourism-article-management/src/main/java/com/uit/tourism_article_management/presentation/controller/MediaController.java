package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.sync.media.upload_resource.UploadResourceCommand;
import com.uit.tourism_article_management.application.command.sync.media.upload_resource.UploadResourceUsecase;
import com.uit.tourism_article_management.application.query.media.LoadCachedResourceQuery;
import com.uit.tourism_article_management.application.query.media.StreamResourceQuery;
import com.uit.tourism_article_management.application.query.media.FetchMetadataQuery;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import com.uit.tourism_article_management.presentation.dto.MediaResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final ResourceLoader resourceLoader;

    private final UploadResourceUsecase uploadResourceUsecase;
    private final StreamResourceQuery downloadByIdQuery;
    private final LoadCachedResourceQuery loadCachedResourceQuery;
    private final FetchMetadataQuery fetchMetadataQuery;

    public MediaController(
            ResourceLoader resourceLoader,
            UploadResourceUsecase uploadResourceUsecase,
            StreamResourceQuery downloadByIdQuery,
            LoadCachedResourceQuery loadCachedResourceQuery,
            FetchMetadataQuery fetchMetadataQuery
    ) {
        this.resourceLoader = resourceLoader;
        this.uploadResourceUsecase = uploadResourceUsecase;
        this.downloadByIdQuery = downloadByIdQuery;
        this.loadCachedResourceQuery = loadCachedResourceQuery;
        this.fetchMetadataQuery = fetchMetadataQuery;
    }

    @PostMapping(consumes = "image/*")
    public ResponseEntity<ApiResponse> upload(
            @RequestHeader("Content-Type") MediaType contentType,
            InputStream stream
    ){
        var result = this.uploadResourceUsecase.execute(new UploadResourceCommand(contentType.toString(), stream));

        URI selfLink = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .pathSegment("media")
                .pathSegment(result.mediaId())
                .build()
                .toUri();

        return ResponseEntity.created(selfLink)
                .body(ApiResponse.builder()
                        .result(result)
                        .build()
                );
    }

//    @GetMapping(value = "/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
         this.downloadByIdQuery.execute(id, response.getOutputStream());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity download(@PathVariable String id, @RequestHeader HttpHeaders header) throws IOException {
        var media = MediaResponse.fromDomain(this.fetchMetadataQuery.execute(id));

        // Spring has handled the not modified case
//        if (header.getIfModifiedSince() >= media.lastModified())
//            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
//        if (header.getIfNoneMatch().contains(media.checksum()))
//            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();

        var cachePath = this.loadCachedResourceQuery.execute(id);
        Resource resource = resourceLoader.getResource(cachePath);

        // Spring has handled the range request case
//        if (media.isStreaming())
//            return this.buildStreamingResponse(header, media, resource);

        return ResponseEntity.ok()
                .contentType(media.type())
                .contentLength(resource.contentLength())
                .lastModified(media.lastModified())
                .eTag(media.checksum())
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)
                        .cachePublic()
                        .immutable()
                )
                .body(resource);
    }

    private ResponseEntity buildStreamingResponse(HttpHeaders header, MediaResponse media, Resource resource) throws IOException {
        System.out.println("What the hell?");
        System.out.println(media.isStreaming());
        long contentLength = resource.contentLength();
        long chunkSize = 1024 * 1024L;

        if (header.getRange().isEmpty())
        {
            long rangeLength = Math.min(chunkSize, contentLength);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(media.type())
                    .lastModified(media.lastModified())
                    .eTag(media.checksum())
                    .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)
                            .cachePublic()
                            .immutable()
                    )
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", 0, rangeLength - 1, contentLength))
                    .body(new ResourceRegion(resource, 0, rangeLength));
        }

        HttpRange range = header.getRange().getFirst();
        long start = range.getRangeStart(contentLength);
        long end = range.getRangeEnd(contentLength);
        long rangeLength = Math.min(chunkSize, end - start + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(media.type())
                .lastModified(media.lastModified())
                .eTag(media.checksum())
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)
                        .cachePublic()
                        .immutable()
                )
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", start, start + rangeLength - 1, contentLength))
                .body(new ResourceRegion(resource, start, rangeLength));
    }
}