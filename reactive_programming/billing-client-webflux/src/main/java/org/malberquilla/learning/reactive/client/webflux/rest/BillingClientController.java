package org.malberquilla.learning.reactive.client.webflux.rest;

import java.util.List;

import org.malberquilla.learning.reactive.billing.bo.Billing;
import org.malberquilla.learning.reactive.client.webflux.service.BillingClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Flux;

@Controller
public class BillingClientController {

    private final BillingClientService billingClientService;

    @Autowired
    public BillingClientController(BillingClientService billingClientService) {
        this.billingClientService = billingClientService;
    }

    @RequestMapping("/all")
    public String getAllBillings(Model model) {
        Flux<Billing> billingFlux = this.billingClientService.getAll();
        List<Billing> billings = billingFlux.collectList().block();
        model.addAttribute("billingList", billings);
        return "billings";
    }
}
