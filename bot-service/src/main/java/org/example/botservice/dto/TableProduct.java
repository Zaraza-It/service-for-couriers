package org.example.botservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.PersistenceCreator;

@Data
public class TableProduct {

    private String product;

public TableProduct() { }

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
    this.product = product;
    }


public TableProduct(String product) {
    this.product = product;
}

}
