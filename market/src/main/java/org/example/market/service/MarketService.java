package org.example.market.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
import org.example.market.exception.NotFoundProductException;
import org.example.market.exception.NotFoundUserException;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void createProduct(
            final String username,
            @Valid final ProductRequest request
    ) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundUserException("User not found");
        }
        Product product = Product.builder()
                .categoryProduct(validProductCategory(request.getCategoryProduct()))
                .productPrice(request.getProductPrice())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .user(user.get())
                .build();

        productsRepository.save(product);
    }

    private ProductCategory validProductCategory(String category) {
        try {
            return ProductCategory.valueOf(category);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
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

    @Transactional
    public void deleteProductByUser(final Long id, final String username) throws NotFoundProductException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundUserException("User not found");
        }
        List<Product> products = productsRepository.findAllByUser(user.get());
        if (products.isEmpty()) return;

        Long productId = searchProduct(products, id);
        if (productId != null) productsRepository.deleteById(productId);
    }


    public static Long searchProduct(List<Product> products, Long id) {
        return products.stream()
                .filter(Objects::nonNull)
                .filter(product -> id.equals(product.getProductId()))
                .findFirst()
                .map(Product::getProductId)
                .orElseGet(() -> {
                    logger.error("Продукт с id {} не был найден", id);
                    return null;
                });
    }


    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productsRepository.findAllProducts();
        if (products == null || products.isEmpty()) {
            throw new NotFoundProductException("Список продуктов пуст");
        }
        return products;
    }


    public void buyProduct(final String username, final Long id, final Integer quantity, final String address) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Product> product = productsRepository.findProductByProductId(id);
        buyProductsAbstract(user, product, quantity, address);
    }


    public void updateProduct(final Long productId, final String username, ProductUpdateRequest request) throws IllegalArgumentException {
            Optional<Product> product = productsRepository.findProductByUsernameAndProductId(username, productId);
            if (userRepository.findByUsername(username).isPresent()
                    && product.isPresent()) {

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
    BigDecimal userBalance = user.get().getBalance();
    Integer resultQuantity = product.get().getQuantity() - quantity;
    BigDecimal resultQuantityAndPrice =  userBalance.multiply(BigDecimal.valueOf(quantity));
    int resultQuantityAndPriceInt = resultQuantityAndPrice.compareTo(user.get().getBalance());
    BigDecimal resultBalance = userBalance.subtract(resultQuantityAndPrice);

    if (product.isPresent() && user.isPresent()) {

        if (quantity <= product.get().getQuantity()
            && product.get().getQuantity() > 0) {

              product.get().setQuantity(resultQuantity);

            if (productOwner != user
               && productOwner.get().getBalance() != null
               && resultQuantityAndPriceInt == -1
               || resultQuantityAndPriceInt == 0 ) {

                 productOwner.get().setBalance(
                    productOwner.get().getBalance()
                   .add(resultQuantityAndPrice)
                 );

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



