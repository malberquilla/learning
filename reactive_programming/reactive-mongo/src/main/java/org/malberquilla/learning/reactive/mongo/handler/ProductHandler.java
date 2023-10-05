package org.malberquilla.learning.reactive.mongo.handler;

import java.net.URI;

import org.malberquilla.learning.reactive.mongo.domain.Product;
import org.malberquilla.learning.reactive.mongo.dto.ProductDto;
import org.malberquilla.learning.reactive.mongo.mapper.UtilProduct;
import org.malberquilla.learning.reactive.mongo.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    private final ProductService service;

    public ProductHandler(ProductService service) {
        this.service = service;
    }

    public Mono<ServerResponse> all(ServerRequest req) { //NOSONAR
        return ServerResponse.ok().body(this.service.findAll(), ProductDto.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(ProductDto.class)
            .flatMap(p -> this.service.save(UtilProduct.dtoToDomain(p)))
            .flatMap(
                p -> ServerResponse.created(URI.create("/handler/product" + p.getId())).build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return ServerResponse.noContent().build(this.service.deleteById(req.pathVariable("id")));
    }

    public Mono<ServerResponse> getById(ServerRequest req) {
        return this.service.findById(req.pathVariable("id"))
            .flatMap(p -> ServerResponse.ok().body(Mono.just(p), ProductDto.class))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return Mono
            .zip(
                data -> {
                    Product domain = (Product) data[0];
                    ProductDto dto = (ProductDto) data[1];
                    domain.setDetail(dto.detail());
                    domain.setPrice(dto.price());
                    return domain;
                },
                this.service.findById(req.pathVariable("id")),
                req.bodyToMono(ProductDto.class)
            )
            .cast(Product.class)
            .flatMap(this.service::save)
            .flatMap(p -> ServerResponse.noContent().build());
    }
}
