package org.malberquilla.learning.reactive.billing.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Billing {

    private double amount;
    private Client client;
    private String detail;
    private int id;
    private List<Product> products;

    public void addProduct(Product product) {
        products.add(product);
    }
}

