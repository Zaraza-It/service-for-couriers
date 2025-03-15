package org.example.market.service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.dto.request.ProductRequest;
import org.example.market.entity.Product;
import org.example.market.entity.User;
import org.example.market.exceptions.ProductNotFoundException;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.UserRepository;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MarketService {
    public static final Logger logger = LogManager.getLogger(MarketService.class);
    private final ProductsRepository productsRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Optional<ProductResponseDTO> getAllProducts () {
        return productsRepository.findAllProducts();
    }

    public void createProduct (@NotBlank String accessToken, @Valid ProductRequest request) {
       try {
           final String username =  jwtService.getAccessClaims(accessToken).getSubject();
           if (userRepository.findByUsername(username) != null) {
              Product product = Product.builder()
                       .categoryProduct(request.getCategoryProduct())
                       .productPrice(request.getProductPrice())
                       .productName(request.getProductName())
                       .quantity(request.getQuantity())
                       .build();
              List<Product> products = new ArrayList<>();
              products.add(product);
              User user = userRepository.findByUsername(username);
              user.setProduct(products);
              userRepository.save(user);
           }
       } catch (Exception e){
           logger.error(e);
       }
    }


public List<Product> filterProductByCategory (String categoryProduct) {
    try {
        List<Product> products = productsRepository.findAllByCategoryProduct(categoryProduct);
        return products;
    } catch (Exception e) {
logger.error(e);
    }
    return null;
}



 }





