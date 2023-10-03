package org.malberquilla.learning.reactive.mongo.repository;

import org.malberquilla.learning.reactive.mongo.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {
}
