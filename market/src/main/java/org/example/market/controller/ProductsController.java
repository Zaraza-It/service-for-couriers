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
import org.example.market.service.ImageService;
import org.example.market.service.MarketService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductsController {

    private final MarketService marketService;
    private final ImageService imageService;

    @PostMapping("/create/")
    public ResponseEntity<Void> createProduct (
      @NotBlank @RequestHeader(name = "AccessToken") String accessToken,
      @Valid @RequestBody ProductRequest request) {
         marketService.createProduct(accessToken,request);
         return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<Void> deleteProduct (
    @NotBlank @RequestHeader(name = "AccessToken") String token,
    @Positive @RequestHeader("Id") Long id) {
        marketService.deleteProductByUser(id,token);
        return ResponseEntity.ok().build();
    }

  @GetMapping("/get")
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
public ResponseEntity<String> updateProduct (
        @NotBlank @RequestHeader("AccessToken") String token,
        @Valid @RequestBody ProductUpdateRequest request,
        @Positive @RequestParam Long id) {
    marketService.updateProduct(id,token,request);
    return ResponseEntity.ok("Товар успешно обновлён!");
    }

@PostMapping("/upload")
public ResponseEntity<String> uploadImageInProduct(
        MultipartFile file,
      @Positive Long id,
      @NotBlank @RequestHeader(name = "AccessToken") String token) {
    if (file.isEmpty()) {
        return ResponseEntity.badRequest().body("Файл не найден");
    }
    imageService.addImageInProduct(file,token,id);
    return ResponseEntity.ok().body("Success Upload!");
}



}