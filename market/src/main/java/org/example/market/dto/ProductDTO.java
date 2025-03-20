package org.example.market.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.market.entity.User;
import org.springframework.data.annotation.PersistenceCreator;


@Data
@AllArgsConstructor
public class ProductDTO {

    @Column(name = "product_name")
    @NotEmpty
    private String productName;
    @Column(name = "username")
    private String username;

    private byte[] image;

@PersistenceCreator
    public ProductDTO() {
       this.productName =  productName;
        this.username = username;
        this.image = image;
    }

}
