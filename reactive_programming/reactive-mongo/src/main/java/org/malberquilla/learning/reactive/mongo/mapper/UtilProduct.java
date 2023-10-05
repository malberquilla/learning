package org.malberquilla.learning.reactive.mongo.mapper;

import org.malberquilla.learning.reactive.mongo.domain.Product;
import org.malberquilla.learning.reactive.mongo.dto.ProductDto;

public final class UtilProduct {

    private UtilProduct() {
    }

    public static ProductDto domainToDto(Product domain) {
        return new ProductDto(domain.getId(), domain.getDetail(), domain.getPrice());
    }

    public static Product dtoToDomain(ProductDto product) {
        return Product.builder()
            .detail(product.detail())
            .price(product.price())
            .build();
    }
}
