package org.malberquilla.learning.reactive.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collation = "products")
@Data
@Builder
public class ProductEntity {
    private String detail;
    private String id;
    private double price;
}

