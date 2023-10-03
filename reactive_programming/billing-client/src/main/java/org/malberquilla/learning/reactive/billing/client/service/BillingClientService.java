package org.malberquilla.learning.reactive.billing.client.service;

import java.util.Arrays;
import java.util.List;

import org.malberquilla.learning.reactive.billing.bo.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

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

    public List<Billing> getAll() {

        long start = System.nanoTime();

        Billing[] billings = this.template.getForObject(this.billingServer,
            Billing[].class);

        long end = System.nanoTime();
        log.info("Time: {}", end - start);

        return Arrays.asList(billings);
    }
}
