package org.example.market.service.admin;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.apache.bcel.classfile.Module;
import org.example.market.dto.request.ProductRequest;
import org.example.market.entity.Product;
import org.example.market.entity.enums.ProductCategory;
import org.example.market.exception.NotFoundUserException;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.example.market.service.JwtService;
import org.example.market.service.MarketService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ProductsRepository productsRepository;
    private final SoldProductRepository soldProductRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private ProductCategory productCategory;
    public static final Logger logger = LogManager.getLogger(MarketService.class);


 public void EditProduct(String token, Long id, ProductRequest request) {
    try {
        String username = jwtService.getAccessClaims(token).getSubject();
        Optional<Product> product = productsRepository.findProductByProductId(id);
        if (username != null) {

        if (nonNull(request.getProductName())) {
            product.get().setProductName(request.getProductName());
        }
        if (nonNull(request.getProductPrice())) {
            product.get().setProductPrice(request.getProductPrice());
        }
        if (nonNull(request.getQuantity())) {
            product.get().setQuantity(request.getQuantity());
        }
        if (productCategory.name().equals(request.getCategoryProduct()) == true) {
            product.get().setCategoryProduct(productCategory);
        }

        productsRepository.save(product.get());

    }
} catch (Exception e) {
    logger.error(e.getMessage());
    }
}





}
