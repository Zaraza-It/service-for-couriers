package org.example.market.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.example.market.entity.enums.StatusProduct;

@Entity
@Table(name = "purchased_product")
@Data
@Builder
public class PurchasedAndSoldProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username_seller",referencedColumnName = "username")
    @JoinColumn(name = "id_product",referencedColumnName = "product_id")
    @JoinColumn(name = "price", referencedColumnName = "product_price")
   private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    private StatusProduct statusProduct;

    @Column(name = "buyer_username")
    private String usernameBuyer;

    @Column(name = "buyer_id")
    private String buyer_id;
}
