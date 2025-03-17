package org.example.market.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.market.entity.enums.StatusProduct;

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

    @Enumerated(EnumType.STRING)
    private StatusProduct statusProduct;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JoinColumn(name = "username",referencedColumnName = "username")
    private User user;


}
