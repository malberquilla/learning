package org.malberquilla.learning.reactive.mongo.service;

import org.malberquilla.learning.reactive.mongo.entity.ProductEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);

    Mono<ProductEntity> findById(String id);

    Flux<ProductEntity> findAll();

    Mono<ProductEntity> save(ProductEntity product);
}
