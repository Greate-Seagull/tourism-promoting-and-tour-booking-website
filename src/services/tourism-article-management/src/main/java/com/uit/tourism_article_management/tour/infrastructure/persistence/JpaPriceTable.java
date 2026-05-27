package com.uit.tourism_article_management.tour.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "price_tables")
@Data
public class JpaPriceTable {
    private String id;
    private String name;
    private int singleRoomPrice;
}
