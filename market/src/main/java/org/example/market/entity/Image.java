package org.example.market.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JoinColumnOrFormula;

@Table(name = "image")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob @Column(name = "image_data")
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
}
