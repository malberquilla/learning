package org.malberquilla.learning.reactive.mongo.rest;

import org.malberquilla.learning.reactive.mongo.dto.ProductDto;
import org.malberquilla.learning.reactive.mongo.mapper.UtilProduct;
import org.malberquilla.learning.reactive.mongo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProductRestController {

    private final ProductService service;

    @Autowired
    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/product")
    public Mono<ResponseEntity<ProductDto>> addProduct(@Valid ProductDto product) {
        return this.service.save(UtilProduct.dtoToDomain(product))
            .map(p -> ResponseEntity.ok(UtilProduct.domainToDto(p)));
    }

    @GetMapping("/product")
    public Flux<ProductDto> getAll() {
        return service.findAll()
            .map(UtilProduct::domainToDto);
    }

    @GetMapping("/product/{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable("id") String id) {
        return service.findById(id)
            .map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(UtilProduct.domainToDto(p)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
