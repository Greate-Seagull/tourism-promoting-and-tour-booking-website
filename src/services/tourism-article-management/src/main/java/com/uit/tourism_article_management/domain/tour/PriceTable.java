package com.uit.tourism_article_management.domain.tour;

import java.util.List;

public record PriceTable(
        String id,
        String name,
        int singleRoomPrice,
        List<AgeGroupPrice> agePrices
) {

}
