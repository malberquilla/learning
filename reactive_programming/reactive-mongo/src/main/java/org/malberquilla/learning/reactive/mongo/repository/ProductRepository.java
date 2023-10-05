package org.malberquilla.learning.reactive.mongo.repository;

import org.malberquilla.learning.reactive.mongo.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
