package org.example.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;

@Table(name = "image")
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob @Column(name = "image_data")
    private byte[] imageData;
    @Column(name = "image_name")
    private String imageName;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;



    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
