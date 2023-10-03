package org.malberquilla.learning.reactive.billing.client.rest;

import org.malberquilla.learning.reactive.billing.client.service.BillingClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BillingClientController {

    private final BillingClientService billingClientService;

    @Autowired
    public BillingClientController(BillingClientService billingClientService) {
        this.billingClientService = billingClientService;
    }

    @RequestMapping("/all")
    public String getAllBillings(Model model) {
        model.addAttribute("billingList", this.billingClientService.getAll());
        return "billings";
    }
}
