package org.example.botservice.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.PersistenceCreator;
@AllArgsConstructor
@Setter
@Getter
public class TableProduct {
    private String product;
}
