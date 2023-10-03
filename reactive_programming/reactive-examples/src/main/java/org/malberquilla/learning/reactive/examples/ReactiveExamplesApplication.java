package org.malberquilla.learning.reactive.examples;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.malberquilla.learning.reactive.billing.bo.Billing;
import org.malberquilla.learning.reactive.billing.bo.Client;
import org.malberquilla.learning.reactive.billing.bo.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Log4j2
public class ReactiveExamplesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveExamplesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        intFlows();
        billingFlows();
        intervalExample();
    }

    private void intFlows() {
        // flow of numbers
        Flux<Integer> numbers = Flux.just(1, 2, 3, 4, 5);
        // We need to subscribe to the flow
        numbers.subscribe(log::info);

        Flux<Integer> fluxWithException = Flux.just(1, 2, 3, 4, 5)
            .doOnNext(item -> {
                if (item % 2 == 0) {
                    throw new RuntimeException("Even numbers are not allowed");
                }
            });
        fluxWithException.subscribe(log::info, log::error, () -> log.info("End"));

        Flux<Integer> mapFlow = Flux.just(1, 2, 3, 4, 5)
            .map(i -> i * 2)
            .doOnNext(log::info)
            .map(i -> i / 2);

        mapFlow.subscribe(log::info);
    }

    private void billingFlows() {
        List<Billing> list = Arrays.asList(
            Billing.builder().id(0).amount(100).detail("jfdalkd").build(),
            Billing.builder().id(1).amount(200).detail("erewerwer").build(),
            Billing.builder().id(2)
                .amount(400)
                .detail("zxcvzxcv")
                .build()
        );

        Flux<Billing> billingFlux = Flux.fromIterable(list)
            .filter(b -> b.getAmount() >= 200)
            .doOnNext(b -> b.setDetail(b.getDetail().toUpperCase()));

        billingFlux.subscribe(log::info);

        Flux.fromIterable(list)
            .collectList()
            .subscribe(l -> l.forEach(log::info));

        Mono<Client> clientMono = Mono.fromCallable(
            () -> Client.builder().name("Perri").cif("9999999F").build());
        Flux<Product> productFlux = Flux.just(
            Product.builder().id(0).detail("Display").price(129.95).build(),
            Product.builder().id(1).detail("Keyboard").price(109.90).build(),
            Product.builder().id(2).detail("Mouse").price(89.95).build(),
            Product.builder().id(3).detail("Printer").price(47.98).build()
        );

        Mono<Billing> billingMono = clientMono
            .flatMap(client -> productFlux.collectList()
                .map(products -> Billing.builder()
                    .client(client)
                    .products(products)
                    .build()));

        billingMono.subscribe(log::info);

        Mono<Billing> billingZipWith =
            clientMono.zipWith(
                productFlux.collectList(),
                this::createBillingWithClientAndProducts
            );

        billingZipWith.subscribe(log::info);
    }

    public void intervalExample() {
        Flux<Integer> range = Flux.range(1, 10);
        Flux<Long> delays = Flux.interval(Duration.ofSeconds(1));

        range.zipWith(delays, (a, b) -> a)
            .doOnNext(log::info)
            .subscribe();
        // blocks execution until the flow finishes
        //.blockLast();

        log.info("End of intervalExample");
    }

    private Billing createBillingWithClientAndProducts(Client client,
        List<Product> products) {
        return Billing.builder().client(client).products(products).build();
    }
}
