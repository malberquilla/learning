package org.malberquilla.learning.reactive.mongo.config;

import org.malberquilla.learning.reactive.mongo.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfiguration {

    public static final String PATH_PRODUCT = "/handler/product";
    public static final String PATH_PRODUCT_BY_ID = "/handler/product/{id}";

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return route(GET(PATH_PRODUCT), handler::all)
            .andRoute(GET(PATH_PRODUCT_BY_ID), handler::getById)
            .andRoute(POST(PATH_PRODUCT), handler::create)
            .andRoute(PUT(PATH_PRODUCT_BY_ID), handler::update)
            .andRoute(DELETE(PATH_PRODUCT_BY_ID), handler::delete);
    }
}
