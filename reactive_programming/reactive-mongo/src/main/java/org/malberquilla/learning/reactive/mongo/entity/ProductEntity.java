package org.malberquilla.learning.reactive.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "products")
@Data
@Builder
public class ProductEntity {
    private String detail;
    @Id
    private String id;
    private double price;
}

