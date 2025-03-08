package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.example.market.controller.dto.ProductResponseDTO;
import org.example.market.entity.Product;
import org.example.market.exceptions.ProductNotFoundException;
import org.example.market.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final ProductsRepository productsRepository;


    public List<ProductResponseDTO> getAllProducts () {
        return productsRepository.findAll();
    }

    public Product getProductById (Long productId) {
        return productsRepository
                .findById(productId)
                .orElseThrow(() ->  new ProductNotFoundException("Товар не был найден"));
    }
}
