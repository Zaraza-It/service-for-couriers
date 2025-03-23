package org.example.market.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;
import org.example.market.entity.enums.StatusProduct;
import org.example.market.entity.enums.StatusUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    @Column(name = "product_id")
    private Long productId;

    @Min(5)
    @Column(name = "product_name")
    private String productName;

    @Column(name = "category_product")
    @Min(value = 5)
    private String categoryProduct;

    @Positive
    @Column(name = "quantity")
    private Integer quantity;

    @Positive
    @Column(name = "product_price")
    private BigDecimal productPrice;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            @JoinColumn(name = "username",referencedColumnName = "username")
    })
    private User user;

    @OneToMany(mappedBy = "product",targetEntity = SoldProduct.class)
    private Set<SoldProduct> soldProducts;

    @OneToOne(cascade =CascadeType.ALL,targetEntity = Image.class)
    @JoinColumns({
    @JoinColumn(name = "image_id",referencedColumnName = "id",nullable = true),
    @JoinColumn(name = "image_data", referencedColumnName = "image_data",nullable = true)})
    private Image image;

}
