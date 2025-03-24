package org.example.market.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.market.dto.request.ProductRequest;
import org.example.market.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
private final AdminService adminService;


@PostMapping("/update/product/{id}")
public ResponseEntity<Void> updateProduct(
        @PathVariable("id") Long id,
        @NotBlank @RequestHeader(name = "AccessToken") String token,
        @Valid ProductRequest productRequest) {
    adminService.EditProduct(token,id,productRequest);
    return ResponseEntity.ok().build();
}

}
