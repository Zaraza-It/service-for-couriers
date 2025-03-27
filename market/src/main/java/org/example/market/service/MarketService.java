package org.example.market.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.dto.EmailDTO;
import org.example.market.dto.KafkaOrderDTO;
import org.example.market.dto.ProductDTO;
import org.example.market.dto.request.ProductRequest;
import org.example.market.dto.request.ProductUpdateRequest;
import org.example.market.entity.Product;
import org.example.market.entity.SoldProduct;
import org.example.market.entity.User;
import org.example.market.entity.enums.ProductCategory;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
    public static final Logger logger = LogManager.getLogger(MarketService.class);
    private final ProductsRepository productsRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SoldProductRepository soldProductRepository;
    private final ImageRepository imageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, EmailDTO> kafkaTemplate;
    private final KafkaTemplate<String, KafkaOrderDTO> orderKafkaTemplate;
    private final KafkaSender kafkaSender;

    public void createProduct(
            @NotBlank String accessToken,
            @Valid ProductRequest request) throws IllegalArgumentException
    {
        try {
            final String username = jwtService.getAccessClaims(accessToken).getSubject();
            logger.info(username);
            if (userRepository.findByUsername(username) != null) {

                Product product = Product.builder()
                        .categoryProduct(ProductCategory.valueOf(request.getCategoryProduct()))
                        .productPrice(request.getProductPrice())
                        .productName(request.getProductName())
                        .quantity(request.getQuantity())
                        .build();
                Optional<User> user = userRepository.findByUsername(username);
                product.setUser(user.get());
                productsRepository.save(product);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public List<Product> filterProductByCategory(String categoryProduct) {
        try {
            List<Product> products = productsRepository.findAllByCategoryProduct(categoryProduct);
            return products;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    public void deleteProductByUser(Long id, String token) {
        try {
            String username = jwtService.getAccessClaims(token).getSubject();
            if (userRepository.findByUsername(username) != null) {
                Optional<User> user = userRepository.findByUsername(username);
                if (productsRepository.findAllByUser(user.get()) != null) {
                    List<Product> products = productsRepository.findAllByUser(user.get());
                    Long idSearch = searchProduct(products, id);
                    if (idSearch != null) {
                        productsRepository.deleteById(idSearch);
                    } else logger.info("Product not found");
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static Long searchProduct(List<Product> products, Long id) {
        for (int i = 0; i < products.size(); i++) {
            Long result = products.get(i).getProductId();
            if (result.equals(id)) {
                return result;
            } else logger.info(result + " Error");

        }
        return null;
    }


    public Optional<ProductDTO> getAllProducts() {
        try {
            Optional<ProductDTO> products = productsRepository.findAllProducts();
            return products;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    public void buyProduct(String token, Long id, Integer quantity,String address) {
        try {
            final String username = jwtService.getAccessClaims(token).getSubject();
            Optional<User> user = userRepository.findByUsername(username);
            Optional<Product> product = productsRepository.findProductByProductId(id);
                buyProductsAbstract(user,product,quantity,address);

        } catch (Exception e) {
            logger.error(e);
        }
    }
//if (product.getQuantity().equals(0)) {
//                    soldProductRepository.delete();

    public void updateProduct(Long productId, String token, ProductUpdateRequest request) throws IllegalArgumentException {
        try {
            final String username = jwtService.getAccessClaims(token).getSubject();
            if (userRepository.findByUsername(username) != null) {
               Optional<Product> product = productsRepository.findProductByUsernameAndProductId(username, productId);
                if (product != null) {
                    logger.info(product);
                    if (request.getProductName() != null) {
                        product.get().setProductName(request.getProductName());
                    }
                    if (request.getQuantity() != null) {
                        product.get().setQuantity(request.getQuantity());
                    }
                    if (request.getPrice() != null) {
                        product.get().setProductPrice(request.getPrice());
                    }
                    if (request.getCategoryProduct() != null) {
                        product.get().setCategoryProduct(ProductCategory.valueOf(request.getCategoryProduct()));
                    }
                    productsRepository.save(product.get());
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static String generateUnId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public SoldProduct buildSoldProduct(
            Optional<User> productOwner,
            Optional<User> user,Optional<Product> product,
            Integer resultQuantity)
    {
        SoldProduct soldProduct = SoldProduct.builder()
            .nameSeller(productOwner.get().getUsername())
            .nameBuyer(user.get().getUsername())
            .product(product.get())
            .id_buyer(user.get().getId())
            .id_seller(productOwner.get().getId())
            .quantity(resultQuantity)
            .uniqueId(generateUnId().toString())
            .build();
        return soldProduct;
    }
@Transactional
public void buyProductsAbstract(Optional<User> user,Optional<Product> product,Integer quantity,String address) {
    Optional<User> productOwner = userRepository.findByUsername(product.get().getUser().getUsername());
    BigDecimal productPrice = product.get().getProductPrice();
    BigDecimal userBalance = user.get().getBalance();
    Integer resultQuantity = product.get().getQuantity() - quantity;
    BigDecimal resultQuantityAndPrice =  userBalance.multiply(BigDecimal.valueOf(quantity));
    int resultQuantityAndPriceInt = resultQuantityAndPrice.compareTo(user.get().getBalance());
    BigDecimal resultBalance = userBalance.subtract(resultQuantityAndPrice);

    if (product.isPresent() && user.isPresent()) {

        if (quantity <= product.get().getQuantity() &&
            product.get().getQuantity() > 0) {

              product.get().setQuantity(resultQuantity);

            if (productOwner != user &&
               productOwner.get().getBalance() != null &&
               resultQuantityAndPriceInt == -1 ||
               resultQuantityAndPriceInt == 0 ) {

                 productOwner.get().setBalance(productOwner.get().getBalance().add(resultQuantityAndPrice));
                 user.get().setBalance(resultBalance);

                if (user.get().getEmail() != null) {
                   SendEmailAboutPurchase(
                        user.get().getUsername(),
                        user.get().getEmail()
                   );
                }


                soldProductRepository.save(buildSoldProduct(productOwner, user, product, resultQuantity));
                userRepository.save(user.get());
                userRepository.save(productOwner.get());
                SendOrder(address,product.get().getProductPrice(),user.get().getUsername(),product.get().getProductName());

                }
            }
        }
    }

    public void SendEmailAboutPurchase(@NotBlank String username,@Email @NotBlank String  email) {
        EmailDTO emailDTO = new EmailDTO(username,email);
        kafkaTemplate.send("topic1", emailDTO);
    }

    public void SendOrder(String address,BigDecimal price,String username,String productName) {
        orderKafkaTemplate.send("topic2", KafkaOrderDTO.builder()
                .address(address)
                .price(price)
                .customerName(username)
                .product(productName)
                .build());
    }



}




