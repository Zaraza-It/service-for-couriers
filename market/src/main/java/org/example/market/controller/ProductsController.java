package org.example.market.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.dto.request.ProductRequest;
import org.example.market.dto.request.ProductUpdateRequest;
import org.example.market.entity.Product;
import org.example.market.service.MarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final MarketService marketService;


    @PostMapping("/products/create")
    public ResponseEntity<Void> createProduct (
      @NotBlank @RequestHeader(name = "AccessToken") String accessToken,
      @Valid @RequestBody ProductRequest request) {
         marketService.createProduct(accessToken,request);
         return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/delete")
    public ResponseEntity<Void> deleteProduct (
    @NotBlank @RequestHeader(name = "AccessToken") String token,
    @Positive @RequestHeader("Id") Long id) {
        marketService.deleteProductByUser(id,token);
        return ResponseEntity.ok().build();
    }

  @GetMapping("/product/get")
  public ResponseEntity<List> getAllProduct(){
  return ResponseEntity.ok()
         .body(marketService.getAllProducts());
  }


  @PostMapping("/buy/")
  public ResponseEntity<String> buyProduct(
         @NotBlank @RequestHeader(name = "AccessToken") String token,
         @NotNull @Positive @RequestParam Long id,
         @NotNull @Positive @RequestParam Integer quantity) {
  marketService.buyProduct(token,id,quantity);
  return ResponseEntity.ok().body("Success Buy!");
  }


@PutMapping("/update/")
public ResponseEntity<String > updateProduct (
        @NotBlank @RequestHeader("AccessToken") String token,
        @Valid @RequestBody ProductUpdateRequest request,
        @Positive @RequestParam Long id) {
    marketService.updateProduct(id,token,request);
    return ResponseEntity.ok("Товар успешно обновлён!");
    }

}