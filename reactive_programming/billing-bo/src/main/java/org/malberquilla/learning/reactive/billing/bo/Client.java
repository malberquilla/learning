package org.malberquilla.learning.reactive.billing.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
    private String cif;
    private String name;
}
