package org.malberquilla.learning.reactive.mongo.service;

import org.malberquilla.learning.reactive.mongo.entity.ProductEntity;
import org.malberquilla.learning.reactive.mongo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(
        ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Mono<ProductEntity> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<ProductEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ProductEntity> save(ProductEntity product) {
        return repository.save(product);
    }
}
