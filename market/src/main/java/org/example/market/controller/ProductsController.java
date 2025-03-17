package org.example.market.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @PostMapping("/products/create")
    public ResponseEntity<Void> createProduct (
     @NotBlank @RequestHeader(name = "AccessToken") String accessToken,
      @Valid @RequestBody ProductRequest request) {
         marketService.createProduct(accessToken,request);
         return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct (
     @NotBlank @RequestHeader(name = "AccessToken") String token,
     @RequestHeader("Id") Long id) {
        marketService.deleteProductByUser(id,token);
        return ResponseEntity.ok().build();
    }



}