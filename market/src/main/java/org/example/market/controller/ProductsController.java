package org.example.market.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.dto.request.ProductRequest;
import org.example.market.entity.Product;
import org.example.market.service.MarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final MarketService marketService;



    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts () {
        return ResponseEntity.ok(marketService.getAllProducts().stream().toList());

    }

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct (
     @NotBlank @RequestHeader(name = "AccessToken") String accessToken,
      @Valid @RequestBody ProductRequest request) {
         marketService.createProduct(accessToken,request);
         return ResponseEntity.ok().body().build();
    }

    @GetMapping("/filter/")
    public ResponseEntity<List<Product>> filterProductsBy (@NotBlank @RequestParam String categoryProduct) {
      List<Product> products = marketService.filterProductByCategory(categoryProduct);
        return ResponseEntity.ok(products);
    }



}