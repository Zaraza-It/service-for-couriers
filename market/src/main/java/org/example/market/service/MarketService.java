package org.example.market.service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.dto.ProductDTO;
import org.example.market.dto.ProductResponseDTO;
import org.example.market.dto.request.ProductRequest;
import org.example.market.entity.Product;
import org.example.market.entity.PurchasedAndSoldProduct;
import org.example.market.entity.User;
import org.example.market.entity.enums.StatusProduct;
import org.example.market.entity.enums.StatusUser;
import org.example.market.exceptions.ProductNotFoundException;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.RepositoryProductSold;
import org.example.market.repository.UserRepository;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MarketService {
    public static final Logger logger = LogManager.getLogger(MarketService.class);
    private final ProductsRepository productsRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RepositoryProductSold repositoryProductSold;

    public void createProduct (@NotBlank String accessToken, @Valid ProductRequest request) {
       try {
           final String username =  jwtService.getAccessClaims(accessToken).getSubject();
           logger.info(username);
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
              logger.info(user.getUsername());
              product.setUser(user);
              productsRepository.save(product);

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


public void deleteProductByUser(Long id,String token) {
   try {
      String username =  jwtService.getAccessClaims(token).getSubject();
      if (userRepository.findByUsername(username) != null) {
          User user = userRepository.findByUsername(username);
          logger.info(user.getUsername());
          if (productsRepository.findAllByUser(user) != null ) {
             List<Product> products = productsRepository.findAllByUser(user);
             logger.info(products.getFirst().getProductId());
             Long idSearch = searchProduct(products, id);
              logger.info(idSearch);

              if (idSearch != null) {
                 productsRepository.deleteById(idSearch);
             }
              else logger.info("Product not found");
          }
      }
   } catch (Exception e) {
       logger.error(e);
   }
}

public static Long searchProduct(List<Product> products,Long id) {
        for (int i = 0; i < products.size(); i++){
        Long result = products.get(i).getProductId();
         if (result.equals(id)) {
            return result;
         } else logger.info(result + " Error");

        }
        return null;
}


public List getAllProducts() {
try {
    List<ProductDTO> products = productsRepository.findAllProducts();
        return products;
}catch (Exception e){
    logger.error(e);
}
return null;
    }


public void buyProduct(String token,Long id,Long quantity) {
   try {
    String username =  jwtService.getAccessClaims(token).getSubject();
    if (userRepository.findByUsername(username) != null) {
        User user = userRepository.findByUsername(username);
        Product product = productsRepository.findProductById(id);
        if(product != null) {
            BigDecimal productPrice = product.getProductPrice();
            BigDecimal userBalance = user.getBalance();
            int result =  productPrice.compareTo(userBalance;
            if (result == -1) {
                Long resultQuantity = product.getQuantity() - quantity;
                product.setQuantity(resultQuantity;
                User productOwner = userRepository.findByUsername(product.getUser().getUsername());
                user.setBalance(userBalance.subtract(productPrice));
                productOwner.setBalance(productOwner.getBalance().add(productPrice));
                PurchasedAndSoldProduct purchasedAndSoldProduct = PurchasedAndSoldProduct.builder()
                        .statusProduct(StatusProduct.PURCHASED)
                        .buyer_id(user.getUsername())
                        .product(product)
                        .quantity(Math.toIntExact(resultQuantity))
                        .usernameBuyer(username)
                        .build();

                repositoryProductSold.save(purchasedAndSoldProduct)
                userRepository.save(user);
                userRepository.save(productOwner);
                productsRepository.save(product);

            }
        }
    }
   } catch (Exception e){
       logger.error(e);
   }
}

 }





