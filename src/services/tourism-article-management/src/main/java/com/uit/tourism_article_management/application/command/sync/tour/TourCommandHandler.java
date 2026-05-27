package com.uit.tourism_article_management.application.command.sync.tour;

import com.uit.tourism_article_management.application.port.AccountRepository;
import com.uit.tourism_article_management.application.port.DestinationRepository;
import com.uit.tourism_article_management.application.port.TourRepository;
import com.uit.tourism_article_management.domain.tour.*;

public class TourCommandHandler {
    private final DestinationRepository destinationRepo;
    private final AccountRepository accountRepo;
    private final TourRepository tourRepo;

    public TourCommandHandler(
            DestinationRepository destinationRepo,
            AccountRepository accountRepo,
            TourRepository tourRepo
    ) {
        this.destinationRepo = destinationRepo;
        this.accountRepo = accountRepo;
        this.tourRepo = tourRepo;
    }

    public HostedTour host(TourHostingCommand command) {
        Destination pickUp = this.destinationRepo.getById(command.tourCreation().pickup());
        Destination dropOff = this.destinationRepo.getById(command.tourCreation().dropoff());
        TourOperator tourOperator = this.accountRepo.getTourOperatorById(AccountId.of(command.accountId()));
        HostedTour tour = tourOperator.host(command.tourCreation(), pickUp, dropOff);
        this.tourRepo.save(tour);
        return tour;
    }
}
