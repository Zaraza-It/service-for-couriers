package org.example.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SoldProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_buyer")
    private String nameBuyer;

    private Long id_buyer;

    private Long id_seller;

    private int quantity;

    private String nameSeller;

    private String uniqueId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "product_price", referencedColumnName = "product_price"),
            @JoinColumn(name = "id_product",referencedColumnName = "product_id")

    })
    private Product product;
}
