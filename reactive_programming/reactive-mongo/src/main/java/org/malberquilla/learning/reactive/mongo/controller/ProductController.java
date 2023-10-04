package org.malberquilla.learning.reactive.mongo.controller;

import java.time.Duration;

import org.malberquilla.learning.reactive.mongo.dto.ProductDto;
import org.malberquilla.learning.reactive.mongo.entity.ProductEntity;
import org.malberquilla.learning.reactive.mongo.exception.ProductNotFoundException;
import org.malberquilla.learning.reactive.mongo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Log4j2
public class ProductController {
    public static final String PRODUCTS_VIEW = "products";
    public static final String PRODUCTS_ATTR = PRODUCTS_VIEW;
    public static final String PRODUCT_DTO_ATTR = "productDto";
    public static final String PRODUCT_NEW = "product-new";
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public String addProduct(@Valid @ModelAttribute ProductDto product,
        BindingResult validationResult,
        Model model) {
        log.info(product);

        if (validationResult.hasErrors()) {
            return PRODUCT_NEW;
        } else {
            productService.save(
                    ProductEntity.builder()
                        .id(product.id())
                        .detail(product.detail())
                        .price(product.price()).build())
                .subscribe(log::info);
            return "redirect:/product";
        }
    }

    @DeleteMapping("/product/{id}")
    public Mono<String> deleteProductById(@PathVariable("id") String id) {
        return this.productService.existsById(id)
            .flatMap(exists -> {
                if (Boolean.TRUE.equals(exists)) {
                    return productService.deleteById(id);
                } else {
                    return Mono.error(new ProductNotFoundException("Product not found"));
                }
            })
            .then(Mono.just("redirect:/product"))
            .onErrorResume(error -> Mono.just("product-error"));
    }

    @GetMapping("/product/{id}")
    public Mono<String> getProductById(@PathVariable("id") String id, Model model) {
        return this.productService.existsById(id)
            .flatMap(exists -> {
                if (Boolean.TRUE.equals(exists)) {
                    Mono<ProductEntity> product = productService.findById(id);
                    model.addAttribute(PRODUCT_DTO_ATTR, product);
                    return Mono.just(PRODUCT_NEW);
                } else {
                    return Mono.error(new ProductNotFoundException("Product not found"));
                }
            })
            .onErrorResume(error -> Mono.just("product-error"));
    }

    @GetMapping("/product")
    public String getProducts(Model model) {
        model.addAttribute(PRODUCTS_ATTR, getProductDtoFlux());
        return PRODUCTS_VIEW;
    }

    private Flux<ProductDto> getProductDtoFlux() {
        return productService.findAll()
            .map(p -> new ProductDto(p.getId(), p.getDetail().toUpperCase(), p.getPrice()));
    }

    @GetMapping("/product/rdd")
    public String getProductsRDD(Model model) {
        Flux<ProductDto> products = getProductDtoFlux().delayElements(Duration.ofSeconds(2));
        model.addAttribute(PRODUCTS_ATTR, new ReactiveDataDriverContextVariable(products, 2));
        return PRODUCTS_VIEW;
    }

    @GetMapping("/product/repeat")
    public String getProductsRepeat(Model model) {
        Flux<ProductDto> products = getProductDtoFlux().repeat(5000);
        model.addAttribute(PRODUCTS_ATTR, products);
        return PRODUCTS_VIEW;
    }

    @GetMapping("/product/new")
    public String showProductNew(Model model) {
        model.addAttribute(PRODUCT_DTO_ATTR, new ProductDto(null, "", 0.0));
        return PRODUCT_NEW;
    }
}
