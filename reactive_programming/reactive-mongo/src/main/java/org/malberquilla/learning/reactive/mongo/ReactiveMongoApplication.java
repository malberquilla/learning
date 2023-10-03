package org.malberquilla.learning.reactive.mongo;

import org.malberquilla.learning.reactive.mongo.entity.ProductEntity;
import org.malberquilla.learning.reactive.mongo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Log4j2
public class ReactiveMongoApplication implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ReactiveMongoApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMongoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Delete old data
        productRepository.deleteAll().subscribe();

        // Initial data loading
        Flux<ProductEntity> productFlux = Flux.just(
            ProductEntity.builder().detail("Display").price(129.95).build(),
            ProductEntity.builder().detail("Keyboard").price(109.90).build(),
            ProductEntity.builder().detail("Mouse").price(89.95).build(),
            ProductEntity.builder().detail("Printer").price(47.98).build()
        );

        productFlux
            .flatMap(productRepository::save)
            .subscribe(log::info);
    }
}