package org.malberquilla.learning.reactive.mongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "products")
@Data
@Builder
public class Product {
    private String detail;
    @Id
    private String id;
    private double price;
}

