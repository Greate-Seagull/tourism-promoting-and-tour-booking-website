package com.uit.tourism_article_management.application.command.sync.tour;

import com.uit.tourism_article_management.presentation.controller.tour.TourCreation;

public record TourHostingCommand(
        String accountId,
        TourCreation tourCreation
) {
}
