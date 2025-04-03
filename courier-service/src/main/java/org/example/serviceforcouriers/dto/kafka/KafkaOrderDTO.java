package org.example.serviceforcouriers.dto.kafka;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaOrderDTO {

    @NotBlank
    @Min(4)
    private String product;

    @NotBlank
    @Min(4)
    private String customerName;

    @NotBlank
    @Min(5)
    private String address;

    @Positive
    private BigDecimal price;
}