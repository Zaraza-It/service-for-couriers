package org.example.market.controller;

import lombok.RequiredArgsConstructor;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.service.MarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductsController {

    private final MarketService marketService;

    public ProductsController(MarketService marketService) {
        this.marketService = marketService;

    }

    @GetMapping("/products")
    public List<ProductResponseDTO> getAllProducts () {
        return marketService.getAllProducts().stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/products/create")
    public Long createProduct (String productName, Long quantity, BigDecimal productPrice) {
        return marketService.createProduct(
                productName,
                quantity,
                productPrice
        );
    }



}