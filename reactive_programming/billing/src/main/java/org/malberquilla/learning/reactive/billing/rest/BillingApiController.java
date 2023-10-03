package org.malberquilla.learning.reactive.billing.rest;

import java.util.Arrays;
import java.util.List;

import org.malberquilla.learning.reactive.billing.bo.Billing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class BillingApiController {

    @GetMapping("/billing")
    public List<Billing> getBillings() throws InterruptedException {
        List<Billing> list = Arrays.asList(
            Billing.builder().id(0).amount(149.95).detail("Some stuff").build(),
            Billing.builder().id(1).amount(50).detail("Other stuff").build(),
            Billing.builder().id(2).amount(300).detail("More stuff").build(),
            Billing.builder().id(3).amount(1255).detail("Office stuff").build(),
            Billing.builder().id(4).amount(13.50).detail("Home stuff").build()
        );

        Thread.sleep(4000);

        return list;
    }
}
