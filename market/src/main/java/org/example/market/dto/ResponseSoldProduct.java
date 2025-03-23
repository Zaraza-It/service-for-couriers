package org.example.market.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

@Data
public class ResponseSoldProduct {

    @Column(name = "quantity")
    private int quantity;

}
