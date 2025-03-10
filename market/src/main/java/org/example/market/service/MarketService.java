package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.entity.Product;
import org.example.market.exceptions.ProductNotFoundException;
import org.example.market.repository.ProductsRepository;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MarketService {

    private final ProductsRepository productsRepository;

    public MarketService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getAllProducts () {
        return productsRepository.findAll();
    }

    public Long createProduct (String productName, Long quantity, BigDecimal productPrice) {
        if (productName == null || quantity == null || productName == null) {
            throw new IllegalArgumentException("Не все параметры были заполнены.");
        }

        return productsRepository.save(new Product(productName, quantity, productPrice))
                .getProductId();
    }

    public List<Product> filterProductByCategory (String categoryProduct) {
       try {
           List<Product> products = productsRepository.findAllByCategoryProduct(categoryProduct);
           return products;
       } catch (Exception e) {
           System.out.println(e);
       }
    }


}
