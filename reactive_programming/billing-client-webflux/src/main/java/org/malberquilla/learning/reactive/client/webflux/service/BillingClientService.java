package org.malberquilla.learning.reactive.client.webflux.service;

import org.malberquilla.learning.reactive.billing.bo.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Service
@Log4j2
public class BillingClientService {

    @Value("${billing.server}")
    private String billingServer;
    public final RestTemplate template;

    @Autowired
    public BillingClientService(RestTemplate template) {
        this.template = template;
    }

    public Flux<Billing> getAll() {

        long start = System.nanoTime();

        WebClient client = WebClient.create(this.billingServer);

        Flux<Billing> billings = client.get().retrieve().bodyToFlux(Billing.class);

        long end = System.nanoTime();
        log.info("Time: {}", end - start);

        return billings;
    }
}
