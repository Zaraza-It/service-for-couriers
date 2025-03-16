package org.example.market.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @Column(name = "product_name")
    private String productName;

    @Column(name = "category_product")
    private String categoryProduct;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


}
