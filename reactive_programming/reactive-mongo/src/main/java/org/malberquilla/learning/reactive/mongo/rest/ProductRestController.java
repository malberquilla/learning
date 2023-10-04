package org.malberquilla.learning.reactive.mongo.rest;

import org.malberquilla.learning.reactive.mongo.dto.ProductDto;
import org.malberquilla.learning.reactive.mongo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ProductRestController {

    private final ProductService service;

    @Autowired
    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/api/product")
    public Flux<ProductDto> getAll() {
        return service.findAll()
            .map(p -> new ProductDto(p.getId(), p.getDetail(), p.getPrice()));
    }
}
