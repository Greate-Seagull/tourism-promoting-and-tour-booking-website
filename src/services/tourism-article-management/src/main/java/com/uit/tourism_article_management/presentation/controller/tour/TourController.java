package com.uit.tourism_article_management.presentation.controller.tour;

import com.uit.tourism_article_management.application.command.sync.tour.TourCommandHandler;
import com.uit.tourism_article_management.application.command.sync.tour.TourHostingCommand;
import com.uit.tourism_article_management.domain.tour.CompleteTour;
import com.uit.tourism_article_management.domain.tour.HostedTour;
import com.uit.tourism_article_management.domain.tour.Tour;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tours")
public class TourController {
    private final TourCommandHandler handler;

    public TourController(TourCommandHandler handler) {
        this.handler = handler;
    }

    public ResponseEntity host(@RequestBody TourCreation request) {
        final HostedTour hosted = handler.host(new TourHostingCommand("abc", request));
        final CompleteTour complete = CompleteTour.fromHosted(hosted);
    }
}
