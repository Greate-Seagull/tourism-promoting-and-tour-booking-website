package com.uit.tourism_article_management.infrastructure.scheduling;

import com.uit.tourism_article_management.application.command.async.job.media.CleanUpOrphanMedia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Slf4j
public class MediaJobScheduler {
    private final CleanUpOrphanMedia cleanUpOrphanMedia;

    public MediaJobScheduler(CleanUpOrphanMedia cleanUpOrphanMedia) {
        this.cleanUpOrphanMedia = cleanUpOrphanMedia;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void runCleanUpOrphan(){
        log.info("Clean up orphan media started");
        var affectedCount = this.cleanUpOrphanMedia.execute();
        log.info("Clean up orphan media finished, affected: {}", affectedCount);
    }
}
