package com.uit.tourism_article_management.tour.domain.price_table;

import com.uit.tourism_article_management.exception.ClientException;

import java.util.*;

public record PriceTable(
        String name,
        List<AgeGroupPrice> agePrices,
        int singleRoomPrice
) {
    public PriceTable {
        requireValidName(name);
        requireValidPrice(singleRoomPrice);
        requireNonDuplicatedAges(agePrices);
        requireNonOverlappingAges(agePrices);
    }

    static private void requireNonDuplicatedAges(List<AgeGroupPrice> agePrices) {
        List<String> errors = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (var group: agePrices) {
            if (!seen.add(group.name()))
                errors.add(String.format("Group price %s has duplicated name", group.name()));
        }
        if (!errors.isEmpty())
            throw new ClientException(errors);
    }

    static private void requireNonOverlappingAges(List<AgeGroupPrice> agePrices) {
        AgeGroupPrice previous = null;
        List<String> errors = new ArrayList<>();
        for(var current: agePrices.stream().sorted(Comparator.comparing(AgeGroupPrice::from)).toList()) {
            if (previous != null && current.from() <= previous.to()) {
                errors.add(String.format("Group price %s has overlapped with %s", current.name(), previous.name()));
            }
            previous = current;
        }
        if (!errors.isEmpty())
            throw new ClientException(errors);
    }

    static void requireValidPrice(int singleRoomPrice) {
        if (singleRoomPrice < 0)
            throw new ClientException("Price table price must not be negative");
    }

    static private void requireValidName(String name) {
        if (name == null || name.isBlank())
            throw new ClientException("Price table name must be provided");
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }
}
