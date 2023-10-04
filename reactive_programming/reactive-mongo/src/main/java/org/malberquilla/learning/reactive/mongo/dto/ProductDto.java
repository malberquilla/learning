package org.malberquilla.learning.reactive.mongo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProductDto(
    String id,
    @Size(min = 3, max = 20, message = "3 to 20 character")
    @NotEmpty String detail,
    @Min(value = 1, message = "Min value is 1€")
    Double price) {
}

