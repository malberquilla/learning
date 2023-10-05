package org.malberquilla.learning.reactive.mongo.service;

import org.malberquilla.learning.reactive.mongo.domain.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);

    Flux<Product> findAll();

    Mono<Product> findById(String id);

    Mono<Product> save(Product product);
}
